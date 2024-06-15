package co.orange.presentation.buy.info

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import co.orange.domain.entity.response.BuyDetailModel
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import kr.genti.core.base.BaseActivity
import kr.genti.core.extension.breakLines
import kr.genti.core.extension.setNumberForm
import kr.genti.core.extension.setOnSingleClickListener
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivityBuyInfoBinding

@AndroidEntryPoint
class BuyInfoActivity :
    BaseActivity<ActivityBuyInfoBinding>(R.layout.activity_buy_info) {
    private val viewModel by viewModels<BuyInfoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initExitBtnListener()
        getIntentInfo()
        setIntentUi(viewModel.mockBuyInfo)
    }

    private fun initExitBtnListener() {
        binding.btnExit.setOnSingleClickListener { finish() }
    }

    private fun getIntentInfo() {
        viewModel.productId = intent.getLongExtra(EXTRA_PRODUCT_ID, -1)
    }

    private fun setIntentUi(item: BuyDetailModel) {
        with(binding) {
            tvInfoTransaction.text = getString(R.string.transaction_id, item.orderId).breakLines()
            ivInfoProduct.load(item.imgUrl)
            tvInfoProductName.text = item.productName
            tvInfoProductPrice.text = item.originPrice.setNumberForm()
            tvInfoSellerNickname.text = item.sellerNickname
            tvInfoDeliveryName.text = item.addressInfo[0].recipient
            tvInfoDeliveryAddress.text = getString(
                R.string.address_format,
                item.addressInfo[0].zipCode,
                item.addressInfo[0].address
            ).breakLines()
            tvInfoDeliveryPhone.text = item.addressInfo[0].phone
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
            productId: Long
        ): Intent = Intent(context, BuyInfoActivity::class.java).apply {
            putExtra(EXTRA_PRODUCT_ID, productId)
        }
    }
}