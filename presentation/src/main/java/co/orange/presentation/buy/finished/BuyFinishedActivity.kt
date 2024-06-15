package co.orange.presentation.buy.finished

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import co.orange.domain.entity.response.BuyInfoModel
import co.orange.presentation.main.MainActivity
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import kr.genti.core.base.BaseActivity
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

    private fun setIntentUi(item: BuyInfoModel) {
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