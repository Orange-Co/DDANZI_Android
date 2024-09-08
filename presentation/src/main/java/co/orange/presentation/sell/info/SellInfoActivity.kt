package co.orange.presentation.sell.info

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import co.orange.core.R
import co.orange.core.base.BaseActivity
import co.orange.core.extension.breakLines
import co.orange.core.extension.convertDateTime
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.setPriceForm
import co.orange.core.extension.stringOf
import co.orange.core.extension.toast
import co.orange.core.state.UiState
import co.orange.domain.entity.response.SellInfoModel
import co.orange.domain.enums.ItemStatus
import co.orange.presentation.sell.confirm.SellConfirmActivity
import coil.load
import com.kkkk.buy.finished.BuyFinishedActivity.Companion.NEW_DATE_PATTERN
import com.kkkk.buy.finished.BuyFinishedActivity.Companion.OLD_DATE_PATTERN
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kr.genti.presentation.databinding.ActivitySellInfoBinding
import kr.genti.presentation.R as featureR

@AndroidEntryPoint
class SellInfoActivity :
    BaseActivity<ActivitySellInfoBinding>(featureR.layout.activity_sell_info) {
    private val viewModel by viewModels<SellInfoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initExitBtnListener()
        initSellConfirmBtnListener()
        getIntentInfo()
        observeGetSellInfoState()
    }

    private fun initExitBtnListener() {
        binding.btnExit.setOnSingleClickListener { finish() }
    }

    private fun initSellConfirmBtnListener() {
        binding.btnSellConfirm.setOnSingleClickListener {
            SellConfirmActivity.createIntent(this, viewModel.orderId).apply {
                startActivity(this)
            }
        }
    }

    private fun getIntentInfo() {
        with(viewModel) {
            itemId = intent.getStringExtra(EXTRA_ITEM_ID).orEmpty()
            getItemDetailInfoFromServer()
        }
    }

    private fun observeGetSellInfoState() {
        viewModel.getSellInfoState.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { state ->
                when (state) {
                    is UiState.Success -> setIntentUi(state.data)
                    is UiState.Failure -> toast(stringOf(R.string.error_msg))
                    else -> return@onEach
                }
            }.launchIn(lifecycleScope)
    }

    private fun setIntentUi(item: SellInfoModel) {
        with(binding) {
            tvInfoTransaction.text = getString(R.string.transaction_id, item.orderId).breakLines()
            ivInfoProduct.load(item.imgUrl)
            tvInfoProductName.text = item.productName
            tvInfoProductPrice.text = item.originPrice.setPriceForm()
            tvInfoBuyerNickname.text = item.buyerNickname
            tvInfoDeliveryName.text = item.addressInfo.recipient
            tvInfoDeliveryAddress.text =
                getString(
                    R.string.address_short_format,
                    item.addressInfo.zipCode,
                    item.addressInfo.address,
                ).breakLines()
            tvInfoDeliveryPhone.text = item.addressInfo.recipientPhone
            tvInfoTransactionMethod.text = item.paymentMethod
            tvInfoTransactionDate.text =
                item.paidAt?.convertDateTime(OLD_DATE_PATTERN, NEW_DATE_PATTERN)
            tvInfoPayKakao.text = item.originPrice.setPriceForm()
            tvInfoPayReal.text = item.salePrice.setPriceForm()
            tvInfoPayTotal.text = item.salePrice.setPriceForm()
        }
        setItemStatus(item.status)
    }

    private fun setItemStatus(status: String) {
        val (infoMsgResId, btnTextResId, isButtonEnabled) =
            when (status) {
                ItemStatus.ON_SALE.name -> {
                    Triple(R.string.sell_info_msg_on_sale, R.string.sell_info_btn_fix, false)
                }

                ItemStatus.ORDERED.name -> {
                    Triple(R.string.sell_info_msg_ordered, R.string.sell_info_btn_fix, true)
                }

                ItemStatus.SHIPPING.name -> {
                    Triple(R.string.buy_info_msg_shipping, R.string.sell_info_btn_shipping, false)
                }

                ItemStatus.COMPLETED.name -> {
                    Triple(R.string.buy_info_msg_completed, R.string.buy_info_btn_completed, false)
                }

                ItemStatus.CANCELLED.name -> {
                    Triple(R.string.buy_info_msg_cancelled, R.string.buy_info_btn_cancelled, false)
                }

                else -> return
            }
        with(binding) {
            tvInfoMessage.setText(infoMsgResId)
            btnSellConfirm.setText(btnTextResId)
            btnSellConfirm.isEnabled = isButtonEnabled
            ivSellToast.isVisible = isButtonEnabled
            if (status == ItemStatus.ON_SALE.name) {
                tvInfoTransaction.isVisible = false
                layoutInfoBuyer.isVisible = false
                layoutInfoDelivery.isVisible = false
                layoutInfoTransaction.isVisible = false
            }
        }
    }

    companion object {
        private const val EXTRA_ITEM_ID = "EXTRA_ITEM_ID"

        @JvmStatic
        fun createIntent(
            context: Context,
            itemId: String,
        ): Intent =
            Intent(context, SellInfoActivity::class.java).apply {
                putExtra(EXTRA_ITEM_ID, itemId)
            }
    }
}
