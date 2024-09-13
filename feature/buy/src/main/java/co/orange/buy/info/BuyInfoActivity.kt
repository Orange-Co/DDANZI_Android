package co.orange.buy.info

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import co.orange.buy.databinding.ActivityBuyInfoBinding
import co.orange.core.R
import co.orange.core.base.BaseActivity
import co.orange.core.extension.breakLines
import co.orange.core.extension.convertDateFormat
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.setPriceForm
import co.orange.core.extension.stringOf
import co.orange.core.extension.toast
import co.orange.core.navigation.NavigationManager
import co.orange.core.state.UiState
import co.orange.domain.entity.response.OrderInfoModel
import co.orange.domain.enums.OrderStatus
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import co.orange.buy.R as featureR

@AndroidEntryPoint
class BuyInfoActivity :
    BaseActivity<ActivityBuyInfoBinding>(featureR.layout.activity_buy_info) {
    @Inject
    lateinit var navigationManager: NavigationManager
    private val viewModel by viewModels<BuyInfoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initExitBtnListener()
        initOrderConfirmBtnListener()
        getIntentInfo()
        observeGetOrderInfoState()
        observePatchOrderConfirmResult()
    }

    private fun initExitBtnListener() {
        binding.btnExit.setOnSingleClickListener { finish() }
    }

    private fun initOrderConfirmBtnListener() {
        binding.btnOrderConfirm.setOnSingleClickListener {
            viewModel.patchOrderConfirmToServer()
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
            tvInfoTransaction.text = getString(R.string.transaction_id, item.orderId).breakLines()
            ivInfoProduct.load(item.imgUrl)
            tvInfoProductName.text = item.productName
            tvInfoProductPrice.text = item.totalPrice.setPriceForm()
            tvInfoSellerNickname.text = item.sellerNickname
            tvInfoDeliveryName.text = item.addressInfo.recipient
            tvInfoDeliveryAddress.text =
                getString(
                    R.string.address_short_format,
                    item.addressInfo.zipCode,
                    item.addressInfo.address,
                ).breakLines()
            tvInfoDeliveryPhone.text = item.addressInfo.recipientPhone
            tvInfoTransactionMethod.text = item.paymentMethod
            tvInfoTransactionDate.text = item.paidAt.convertDateFormat()
            tvInfoPayMoney.text = item.originPrice.setPriceForm()
            tvInfoPayDiscount.text =
                getString(R.string.add_minus, item.discountPrice.setPriceForm())
            tvInfoPayCharge.text = getString(R.string.add_plus, item.charge.setPriceForm())
            tvInfoPayTotal.text = item.totalPrice.setPriceForm()
        }
        setOrderStatus(item.orderStatus)
    }

    private fun setOrderStatus(status: String) {
        val (infoMsgResId, btnTextResId, isButtonEnabled) =
            when (status) {
                OrderStatus.ORDER_PLACED.name -> {
                    Triple(R.string.buy_info_msg_placed, R.string.buy_info_btn_fix, false)
                }

                OrderStatus.COMPLETED.name -> {
                    Triple(R.string.buy_info_msg_completed, R.string.buy_info_btn_completed, false)
                }

                OrderStatus.CANCELLED.name -> {
                    Triple(R.string.buy_info_msg_cancelled, R.string.buy_info_btn_cancelled, false)
                }

                in
                listOf(
                    OrderStatus.SHIPPING.name,
                    OrderStatus.DELAYED_SHIPPING.name,
                    OrderStatus.WARNING.name,
                ),
                -> {
                    Triple(R.string.buy_info_msg_shipping, R.string.buy_info_btn_fix, true)
                }

                else -> return
            }
        with(binding) {
            tvInfoMessage.setText(infoMsgResId)
            btnOrderConfirm.setText(btnTextResId)
            btnOrderConfirm.isEnabled = isButtonEnabled
            ivBuyToast.isVisible = isButtonEnabled
        }
    }

    private fun observePatchOrderConfirmResult() {
        viewModel.patchOrderConfirmResult.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { isSuccess ->
                if (isSuccess) {
                    toast(stringOf(R.string.buy_order_fix_msg))
                    navigationManager.toMainViewWIthClearing(this)
                } else {
                    toast(stringOf(R.string.error_msg))
                }
            }.launchIn(lifecycleScope)
    }

    companion object {
        private const val EXTRA_ORDER_ID = "EXTRA_ORDER_ID"

        @JvmStatic
        fun createIntent(
            context: Context,
            orderId: String,
        ): Intent =
            Intent(context, BuyInfoActivity::class.java).apply {
                putExtra(EXTRA_ORDER_ID, orderId)
            }
    }
}
