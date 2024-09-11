package co.orange.buy.finished

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import co.orange.buy.databinding.ActivityBuyFinishedBinding
import co.orange.buy.info.BuyInfoActivity
import co.orange.core.R
import co.orange.core.amplitude.AmplitudeManager
import co.orange.core.base.BaseActivity
import co.orange.core.extension.breakLines
import co.orange.core.extension.convertDateTime
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.setPriceForm
import co.orange.core.extension.stringOf
import co.orange.core.extension.toast
import co.orange.core.navigation.NavigationManager
import co.orange.core.state.UiState
import co.orange.domain.entity.response.OrderInfoModel
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import co.orange.buy.R as featureR

@AndroidEntryPoint
class BuyFinishedActivity :
    BaseActivity<ActivityBuyFinishedBinding>(featureR.layout.activity_buy_finished) {
    @Inject
    lateinit var navigationManager: NavigationManager

    private val viewModel by viewModels<BuyFinishedViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initReturnBtnListener()
        initDetailBtnListener()
        getIntentInfo()
        observeGetOrderInfoState()
    }

    private fun initView() {
        AmplitudeManager.trackEvent(
            "complete_purchase_adjustment",
            mapOf("order_id" to viewModel.orderId),
        )
    }

    private fun initReturnBtnListener() {
        binding.btnHome.setOnSingleClickListener { navigationManager.toMainViewWIthClearing(this) }
        binding.btnKeepShopping.setOnSingleClickListener {
            AmplitudeManager.trackEvent("click_purchase_adjustment_add")
            navigationManager.toMainViewWIthClearing(this)
        }
    }

    private fun initDetailBtnListener() {
        binding.btnShowDetail.setOnSingleClickListener {
            AmplitudeManager.trackEvent("click_purchase_adjustment_check")
            startActivity(BuyInfoActivity.createIntent(this, viewModel.orderId))
        }
    }

    private fun getIntentInfo() {
        with(viewModel) {
            orderId = intent.getStringExtra(EXTRA_ORDER_ID).orEmpty()
            getOrderInfoFromServer()
        }
    }

    private fun observeGetOrderInfoState() {
        viewModel.getOrderInfoState.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { state ->
                when (state) {
                    is UiState.Success -> setIntentUi(state.data)
                    is UiState.Failure -> toast(stringOf(R.string.error_msg))
                    else -> return@onEach
                }
            }.launchIn(lifecycleScope)
    }

    private fun setIntentUi(item: OrderInfoModel) {
        with(binding) {
            tvFinishedTransaction.text =
                getString(R.string.transaction_id, item.orderId).breakLines()
            ivFinishedItem.load(item.imgUrl)
            tvFinishedItemName.text = item.productName
            tvFinishedItemPrice.text = item.totalPrice.setPriceForm()
            tvFinishedDeliveryName.text = item.addressInfo.recipient
            tvFinishedDeliveryAddress.text =
                getString(
                    R.string.address_short_format,
                    item.addressInfo.zipCode,
                    item.addressInfo.address,
                ).breakLines()
            tvFinishedDeliveryPhone.text = item.addressInfo.recipientPhone
            tvFinishedTransactionMethod.text = item.paymentMethod
            tvFinishedTransactionDate.text =
                item.paidAt.convertDateTime(
                    OLD_DATE_PATTERN,
                    NEW_DATE_PATTERN,
                )
            tvFinishedPayMoney.text = item.originPrice.setPriceForm()
            tvFinishedPayDiscount.text =
                getString(R.string.add_minus, item.discountPrice.setPriceForm())
            tvFinishedPayCharge.text = getString(R.string.add_plus, item.charge.setPriceForm())
            tvFinishedPayTotal.text = item.totalPrice.setPriceForm()
        }
    }

    companion object {
        private const val EXTRA_ORDER_ID = "EXTRA_ORDER_ID"

        const val OLD_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss"
        const val NEW_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss"

        @JvmStatic
        fun createIntent(
            context: Context,
            orderId: String,
        ): Intent =
            Intent(context, BuyFinishedActivity::class.java).apply {
                putExtra(EXTRA_ORDER_ID, orderId)
            }
    }
}
