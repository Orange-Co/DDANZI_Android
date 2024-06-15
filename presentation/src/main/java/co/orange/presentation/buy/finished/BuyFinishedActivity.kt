package co.orange.presentation.buy.finished

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import co.orange.domain.entity.response.BuyDetailModel
import co.orange.presentation.main.MainActivity
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import kr.genti.core.base.BaseActivity
import kr.genti.core.extension.breakLines
import kr.genti.core.extension.setNumberForm
import kr.genti.core.extension.setOnSingleClickListener
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
        // TODO
        binding.btnShowDetail.setOnSingleClickListener { }
    }

    private fun getIntentInfo() {
        viewModel.productId = intent.getLongExtra(EXTRA_PRODUCT_ID, -1)
    }

    private fun setIntentUi(item: BuyDetailModel) {
        with(binding) {
            tvFinishedTransaction.text =
                getString(R.string.transaction_id, item.orderId).breakLines()
            ivFinishedItem.load(item.imgUrl)
            tvFinishedItemName.text = item.productName
            tvFinishedItemPrice.text = item.originPrice.setNumberForm()
            tvFinishedDeliveryName.text = item.addressInfo[0].recipient
            tvFinishedDeliveryAddress.text = getString(
                R.string.address_format,
                item.addressInfo[0].zipCode,
                item.addressInfo[0].address
            ).breakLines()
            tvFinishedDeliveryPhone.text = item.addressInfo[0].phone
            tvFinishedTransactionMethod.text = item.paymentInfo[0].method
            tvFinishedTransactionDate.text = item.paymentInfo[0].completedAt
            tvFinishedPayMoney.text = item.originPrice.setNumberForm()
            tvFinishedPayDiscount.text = getString(R.string.add_minus, item.discountPrice.setNumberForm())
            tvFinishedPayCharge.text = getString(R.string.add_plus, item.charge.setNumberForm())
            tvFinishedPayTotal.text = item.totalPrice.setNumberForm()
        }
    }


    companion object {
        private const val EXTRA_PRODUCT_ID = "EXTRA_PRODUCT_ID"

        @JvmStatic
        fun createIntent(
            context: Context,
            productId: Long
        ): Intent = Intent(context, BuyFinishedActivity::class.java).apply {
            putExtra(EXTRA_PRODUCT_ID, productId)
        }
    }
}