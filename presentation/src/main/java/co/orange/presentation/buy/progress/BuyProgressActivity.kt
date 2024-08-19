package co.orange.presentation.buy.progress

import android.content.Context
import android.content.Intent
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
    }

    override fun onResume() {
        super.onResume()

        getIntentInfo()
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
        // TODO
        with(binding) {
            btnTermServiceDetail.setOnSingleClickListener { }
            btnTermPurchaseDetail.setOnSingleClickListener { }
        }
    }

    private fun initConfirmBtnListener() {
        binding.btnConfirmPurchase.setOnSingleClickListener {
            viewModel.postPayStartToServer()
        }
    }

    private fun getIntentInfo() {
        with(viewModel) {
            if (productId.isEmpty()) {
                productId = intent.getStringExtra(EXTRA_PRODUCT_ID).orEmpty()
                optionList = intent.getLongArrayExtra(EXTRA_OPTION_LIST)?.toList()
            }
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
            if (response != null && response.success == true) {
                // TODO
            } else {
                toast(stringOf(R.string.error_msg))
            }
        }
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
            optionList: Array<Long>? = null,
        ): Intent =
            Intent(context, BuyProgressActivity::class.java).apply {
                putExtra(EXTRA_PRODUCT_ID, productId)
                putExtra(EXTRA_OPTION_LIST, optionList)
            }
    }
}
