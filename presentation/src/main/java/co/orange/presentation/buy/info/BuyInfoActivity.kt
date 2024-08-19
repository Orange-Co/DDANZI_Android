package co.orange.presentation.buy.info

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import co.orange.core.base.BaseActivity
import co.orange.core.extension.breakLines
import co.orange.core.extension.setNumberForm
import co.orange.core.extension.setOnSingleClickListener
import co.orange.domain.entity.response.OrderInfoModel
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivityBuyInfoBinding

@AndroidEntryPoint
class BuyInfoActivity :
    BaseActivity<ActivityBuyInfoBinding>(R.layout.activity_buy_info) {
    private val viewModel by viewModels<BuyInfoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initExitBtnListener()
        initFixPurchaseBtnListener()
        getIntentInfo()
        setIntentUi(viewModel.mockBuyInfo)
    }

    private fun initExitBtnListener() {
        binding.btnExit.setOnSingleClickListener { finish() }
    }

    private fun initFixPurchaseBtnListener() {
        // TODO
        binding.btnFixPurchase.setOnSingleClickListener { }
    }

    private fun getIntentInfo() {
        viewModel.productId = intent.getStringExtra(EXTRA_PRODUCT_ID).orEmpty()
    }

    private fun setIntentUi(item: OrderInfoModel) {
        with(binding) {
            tvInfoTransaction.text = getString(R.string.transaction_id, item.orderId).breakLines()
            ivInfoProduct.load(item.imgUrl)
            tvInfoProductName.text = item.productName
            tvInfoProductPrice.text = item.originPrice.setNumberForm()
            tvInfoSellerNickname.text = item.sellerNickname
            tvInfoDeliveryName.text = item.addressInfo[0].recipient
            tvInfoDeliveryAddress.text =
                getString(
                    R.string.address_format,
                    item.addressInfo[0].zipCode,
                    item.addressInfo[0].address,
                ).breakLines()
            tvInfoDeliveryPhone.text = item.addressInfo[0].recipientPhone
            tvInfoTransactionMethod.text = item.paymentInfo[0].method
            tvInfoTransactionDate.text = item.paymentInfo[0].completedAt
            tvInfoPayMoney.text = item.originPrice.setNumberForm()
            tvInfoPayDiscount.text =
                getString(R.string.add_minus, item.discountPrice.setNumberForm())
            tvInfoPayCharge.text = getString(R.string.add_plus, item.charge.setNumberForm())
            tvInfoPayTotal.text = item.totalPrice.setNumberForm()
        }
    }

    companion object {
        private const val EXTRA_PRODUCT_ID = "EXTRA_PRODUCT_ID"

        @JvmStatic
        fun createIntent(
            context: Context,
            productId: String,
        ): Intent =
            Intent(context, BuyInfoActivity::class.java).apply {
                putExtra(EXTRA_PRODUCT_ID, productId)
            }
    }
}
