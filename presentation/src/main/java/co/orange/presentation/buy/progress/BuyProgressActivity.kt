package co.orange.presentation.buy.progress

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import co.orange.core.base.BaseActivity
import co.orange.core.extension.breakLines
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.setPriceForm
import co.orange.core.extension.stringOf
import co.orange.core.extension.toPhoneFrom
import co.orange.core.extension.toast
import co.orange.core.state.UiState
import co.orange.domain.entity.response.AddressInfoModel
import co.orange.domain.entity.response.BuyProgressModel
import co.orange.presentation.buy.progress.BuyProgressViewModel.Companion.PAY_SUCCESS
import co.orange.presentation.buy.push.BuyPushActivity
import co.orange.presentation.delivery.DeliveryActivity
import co.orange.presentation.setting.SettingActivity.Companion.WEB_TERM_PURCHASE
import co.orange.presentation.setting.SettingActivity.Companion.WEB_TERM_SERVICE
import coil.load
import com.iamport.sdk.domain.core.Iamport
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kr.genti.presentation.BuildConfig.IAMPORT_CODE
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivityBuyProgressBinding
import timber.log.Timber

@AndroidEntryPoint
class BuyProgressActivity :
    BaseActivity<ActivityBuyProgressBinding>(R.layout.activity_buy_progress) {
    private val viewModel by viewModels<BuyProgressViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initExitBtnListener()
        initDeliveryChangeBtnListener()
        initTermDetailBtnListener()
        initConfirmBtnListener()
        observeGetBuyDataState()
        observePostPayStartState()
        observePatchPayEndState()
        observePostOrderState()
    }

    override fun onResume() {
        super.onResume()

        if (!viewModel.isOrderStarted) getIntentInfo()
    }

    private fun initView() {
        binding.vm = viewModel
        Iamport.init(this)
    }

    private fun initExitBtnListener() {
        binding.btnExit.setOnSingleClickListener { finish() }
    }

    private fun initDeliveryChangeBtnListener() {
        with(binding) {
            btnChangeDelivery.setOnSingleClickListener { navigateToAddAddress() }
            btnDeliveryAdd.setOnSingleClickListener { navigateToAddAddress() }
        }
    }

    private fun navigateToAddAddress() {
        Intent(this, DeliveryActivity::class.java).apply {
            startActivity(this)
        }
    }

    private fun initTermDetailBtnListener() {
        with(binding) {
            btnTermServiceDetail.setOnSingleClickListener {
                Intent(Intent.ACTION_VIEW, Uri.parse(WEB_TERM_SERVICE)).apply {
                    startActivity(this)
                }
            }
            btnTermPurchaseDetail.setOnSingleClickListener {
                Intent(Intent.ACTION_VIEW, Uri.parse(WEB_TERM_PURCHASE)).apply {
                    startActivity(this)
                }
            }
        }
    }

    private fun initConfirmBtnListener() {
        binding.btnConfirmPurchase.setOnSingleClickListener {
            viewModel.postPayStartToServer()
        }
    }

    private fun getIntentInfo() {
        with(viewModel) {
            if (productId.isEmpty()) productId = intent.getStringExtra(EXTRA_PRODUCT_ID).orEmpty()
            optionList =
                intent.getIntegerArrayListExtra(EXTRA_OPTION_LIST)?.mapNotNull { it?.toLong() }
                    ?: emptyList()
            getBuyDataFromServer()
        }
    }

    private fun observeGetBuyDataState() {
        viewModel.getBuyDataState.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { state ->
                when (state) {
                    is UiState.Success -> setBuyProgressUi(state.data)

                    is UiState.Failure -> {
                        toast(stringOf(R.string.error_msg))
                        finish()
                    }

                    else -> return@onEach
                }
            }.launchIn(lifecycleScope)
    }

    private fun setBuyProgressUi(item: BuyProgressModel) {
        with(binding) {
            tvConfirmProductName.text = item.productName
            ivConfirmProduct.load(item.imgUrl)
            tvConfirmProductPrice.text = item.totalPrice.setPriceForm()
            tvConfirmPriceMoney.text = item.originPrice.setPriceForm()
            tvConfirmPriceDiscount.text =
                getString(R.string.add_minus, item.discountPrice.setPriceForm())
            tvConfirmPriceCharge.text =
                getString(R.string.add_plus, item.charge.setPriceForm())
            tvConfirmPriceTotal.text = item.totalPrice.setPriceForm()
        }
        setAddress(item.addressInfo)
    }

    private fun setAddress(address: AddressInfoModel) {
        if (address.recipient != null) {
            with(binding) {
                btnDeliveryAdd.isVisible = false
                layoutDeliveryItem.isVisible = true
                tvDeliveryName.text = address.recipient
                tvDeliveryAddress.text =
                    getString(
                        R.string.address_short_format,
                        address.zipCode,
                        address.address,
                    ).breakLines()
                tvDeliveryPhone.text = address.recipientPhone?.toPhoneFrom()
            }
        }
    }

    private fun observePostPayStartState() {
        viewModel.postPayStartState.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { state ->
                when (state) {
                    is UiState.Success -> startIamportPurchase()
                    is UiState.Failure -> toast(stringOf(R.string.error_msg))
                    else -> return@onEach
                }
            }.launchIn(lifecycleScope)
    }

    private fun startIamportPurchase() {
        val request = viewModel.createIamportRequest()
        if (request == null) {
            toast(stringOf(R.string.error_msg))
            return
        }
        Timber.tag("okhttp").d("IAMPORT PURCHASE REQUEST : $request")
        Iamport.payment(
            userCode = IAMPORT_CODE,
            iamPortRequest = request,
        ) { response ->
            Timber.tag("okhttp").d("IAMPORT PURCHASE RESPONSE : $response")
            viewModel.patchPayEndToServer(response?.error_code)
        }
    }

    private fun observePatchPayEndState() {
        viewModel.patchPayEndState.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { state ->
                when (state) {
                    is UiState.Success -> {
                        if (state.data.payStatus == PAY_SUCCESS) {
                            if (viewModel.isOrderCanceled) {
                                viewModel.isOrderCanceled = false
                                return@onEach
                            }
                            viewModel.postToRequestOrderToServer()
                        } else {
                            toast(stringOf(R.string.error_msg))
                        }
                    }

                    is UiState.Failure -> toast(stringOf(R.string.buy_order_error_msg))
                    else -> return@onEach
                }
            }.launchIn(lifecycleScope)
    }

    private fun observePostOrderState() {
        viewModel.postOrderState.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { state ->
                when (state) {
                    is UiState.Success -> {
                        BuyPushActivity.createIntent(this, state.data).apply {
                            startActivity(this)
                        }
                        finish()
                    }

                    is UiState.Failure -> toast(stringOf(R.string.buy_order_error_msg))
                    else -> return@onEach
                }
            }.launchIn(lifecycleScope)
    }

    override fun onDestroy() {
        super.onDestroy()

        Iamport.close()
    }

    companion object {
        private const val EXTRA_PRODUCT_ID = "EXTRA_PRODUCT_ID"
        private const val EXTRA_OPTION_LIST = "EXTRA_OPTION_LIST"

        @JvmStatic
        fun createIntent(
            context: Context,
            productId: String,
            optionList: ArrayList<Int> = arrayListOf(),
        ): Intent =
            Intent(context, BuyProgressActivity::class.java).apply {
                putExtra(EXTRA_PRODUCT_ID, productId)
                putIntegerArrayListExtra(EXTRA_OPTION_LIST, optionList)
            }
    }
}
