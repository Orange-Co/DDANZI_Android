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
import co.orange.core.extension.setNumberForm
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.stringOf
import co.orange.core.extension.toPhoneFrom
import co.orange.core.extension.toast
import co.orange.core.state.UiState
import co.orange.domain.entity.response.AddressInfoModel
import co.orange.domain.entity.response.BuyProgressModel
import co.orange.presentation.auth.phone.TermBottomSheet.Companion.WEB_TERM_SERVICE
import co.orange.presentation.buy.finished.BuyFinishedActivity
import co.orange.presentation.buy.progress.BuyProgressViewModel.Companion.PAY_SUCCESS
import co.orange.presentation.setting.delivery.DeliveryActivity
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
            tvConfirmProductPrice.text = item.originPrice.setNumberForm()
            tvConfirmPriceMoney.text = item.originPrice.setNumberForm()
            tvConfirmPriceDiscount.text =
                getString(R.string.add_minus, item.discountPrice.setNumberForm())
            tvConfirmPriceCharge.text =
                getString(R.string.add_plus, item.charge.setNumberForm())
            tvConfirmPriceTotal.text = item.totalPrice.setNumberForm()
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
            if (response?.imp_uid?.isEmpty() == false) {
                viewModel.patchPayEndToServer(true)
            } else {
                viewModel.patchPayEndToServer(false)
            }
        }
    }

    private fun observePatchPayEndState() {
        viewModel.patchPayEndState.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { state ->
                when (state) {
                    is UiState.Success -> {
                        if (state.data.payStatus == PAY_SUCCESS) {
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
                        toast(stringOf(R.string.buy_order_success_msg))
                        // TODO 추후 푸쉬알림뷰 연결
                        BuyFinishedActivity.createIntent(this, state.data).apply {
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
        const val WEB_TERM_PURCHASE =
            "https://brawny-guan-098.notion.site/56bcbc1ed0f3454ba08fa1070fa5413d?pvs=4"

        private const val EXTRA_PRODUCT_ID = "EXTRA_PRODUCT_ID"

        @JvmStatic
        fun createIntent(
            context: Context,
            productId: String,
        ): Intent =
            Intent(context, BuyProgressActivity::class.java).apply {
                putExtra(EXTRA_PRODUCT_ID, productId)
            }
    }
}
