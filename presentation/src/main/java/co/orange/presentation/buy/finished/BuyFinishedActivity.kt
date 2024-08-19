package co.orange.presentation.buy.finished

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import co.orange.core.base.BaseActivity
import co.orange.core.extension.breakLines
import co.orange.core.extension.setNumberForm
import co.orange.core.extension.setOnSingleClickListener
import co.orange.domain.entity.response.OrderInfoModel
import co.orange.presentation.buy.info.BuyInfoActivity
import co.orange.presentation.main.MainActivity
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivityBuyFinishedBinding

@AndroidEntryPoint
class BuyFinishedActivity :
    BaseActivity<ActivityBuyFinishedBinding>(R.layout.activity_buy_finished) {
    private val viewModel by viewModels<BuyFinishedViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initReturnBtnListener()
        initDetailBtnListener()
        getIntentInfo()
        setIntentUi(viewModel.mockBuyInfo)
    }

    private fun initReturnBtnListener() {
        binding.btnHome.setOnSingleClickListener { navigateToHome() }
        binding.btnKeepShopping.setOnSingleClickListener { navigateToHome() }
    }

    private fun navigateToHome() {
        Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(this)
        }
    }

    private fun initDetailBtnListener() {
        binding.btnShowDetail.setOnSingleClickListener {
            BuyInfoActivity.createIntent(this, viewModel.productId).apply {
                startActivity(this)
            }
        }
    }

    private fun getIntentInfo() {
        viewModel.productId = intent.getStringExtra(EXTRA_ORDER_ID).orEmpty()
    }

    private fun setIntentUi(item: OrderInfoModel) {
        with(binding) {
            tvFinishedTransaction.text =
                getString(R.string.transaction_id, item.orderId).breakLines()
            ivFinishedItem.load(item.imgUrl)
            tvFinishedItemName.text = item.productName
            tvFinishedItemPrice.text = item.originPrice.setNumberForm()
            tvFinishedDeliveryName.text = item.addressInfo[0].recipient
            tvFinishedDeliveryAddress.text =
                getString(
                    R.string.address_short_format,
                    item.addressInfo[0].zipCode,
                    item.addressInfo[0].address,
                ).breakLines()
            tvFinishedDeliveryPhone.text = item.addressInfo[0].recipientPhone
            tvFinishedTransactionMethod.text = item.paymentInfo[0].method
            tvFinishedTransactionDate.text = item.paymentInfo[0].completedAt
            tvFinishedPayMoney.text = item.originPrice.setNumberForm()
            tvFinishedPayDiscount.text =
                getString(R.string.add_minus, item.discountPrice.setNumberForm())
            tvFinishedPayCharge.text = getString(R.string.add_plus, item.charge.setNumberForm())
            tvFinishedPayTotal.text = item.totalPrice.setNumberForm()
        }
    }

    companion object {
        private const val EXTRA_ORDER_ID = "EXTRA_ORDER_ID"

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
