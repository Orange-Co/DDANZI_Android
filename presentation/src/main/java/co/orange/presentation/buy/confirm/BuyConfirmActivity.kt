package co.orange.presentation.buy.confirm

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import androidx.activity.viewModels
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import kr.genti.core.base.BaseActivity
import kr.genti.core.extension.setNumberForm
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivityBuyConfirmBinding

@AndroidEntryPoint
class BuyConfirmActivity : BaseActivity<ActivityBuyConfirmBinding>(R.layout.activity_buy_confirm) {
    private val viewModel by viewModels<BuyConfirmViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getIntentInfo()
        setIntentUi()
    }

    private fun getIntentInfo() {
        with(viewModel) {
            imageUrl = intent.getStringExtra(EXTRA_PRODUCT_URL).orEmpty()
            originPrice = intent.getIntExtra(EXTRA_ORIGIN_PRICE, 0)
            detailPrice = intent.getIntExtra(EXTRA_SALE_PRICE, 0)
            name = intent.getStringExtra(EXTRA_NAME).orEmpty()
        }
    }

    private fun setIntentUi() {
        with(binding) {
            tvConfirmProductName.text = viewModel.name
            ivConfirmProduct.load(viewModel.imageUrl)
            tvConfirmProductPrice.apply {
                text = viewModel.originPrice.setNumberForm()
                setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG)
            }
            tvConfirmPriceMoney.apply {
                text = viewModel.originPrice.setNumberForm()
                setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG)
            }
            // TODO
            tvConfirmPriceDiscount.text = "-3,000"
            tvConfirmPriceCharge.text = "+350"
            tvConfirmPriceTotal.text = "21,350"
        }
    }


    companion object {
        private const val EXTRA_PRODUCT_URL = "EXTRA_PRODUCT_URL"
        private const val EXTRA_ORIGIN_PRICE = "EXTRA_ORIGIN_PRICE"
        private const val EXTRA_SALE_PRICE = "EXTRA_SALE_PRICE"
        private const val EXTRA_NAME = "EXTRA_NAME"

        @JvmStatic
        fun createIntent(
            context: Context,
            productUrl: String,
            originPrice: Int,
            salePrice: Int,
            name: String
        ): Intent = Intent(context, BuyConfirmActivity::class.java).apply {
            putExtra(EXTRA_PRODUCT_URL, productUrl)
            putExtra(EXTRA_ORIGIN_PRICE, originPrice)
            putExtra(EXTRA_SALE_PRICE, salePrice)
            putExtra(EXTRA_NAME, name)
        }
    }
}