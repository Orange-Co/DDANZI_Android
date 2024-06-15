package co.orange.presentation.buy.confirm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import co.orange.domain.entity.response.BuyInfoModel
import co.orange.presentation.buy.push.BuyPushActivity
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import kr.genti.core.base.BaseActivity
import kr.genti.core.extension.setNumberForm
import kr.genti.core.extension.setOnSingleClickListener
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivityBuyConfirmBinding

@AndroidEntryPoint
class BuyConfirmActivity : BaseActivity<ActivityBuyConfirmBinding>(R.layout.activity_buy_confirm) {
    private val viewModel by viewModels<BuyConfirmViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initExitBtnListener()
        initDeliveryChangeBtnListener()
        initTermBtnListener()
        initConfirmBtnListener()
        getIntentInfo()
        setIntentUi(viewModel.mockBuyInfo)
    }

    private fun initExitBtnListener() {
        binding.btnExit.setOnSingleClickListener { finish() }
    }

    private fun initDeliveryChangeBtnListener() {
        // TODO
        binding.btnChangeDelivery.setOnSingleClickListener { }
    }

    private fun initTermBtnListener() {
        //TODO
        binding.btnTermAll.setOnSingleClickListener { }
        binding.btnTermFirst.setOnSingleClickListener { }
        binding.btnTermSecond.setOnSingleClickListener { }
        binding.btnTermThird.setOnSingleClickListener { }
    }

    private fun initConfirmBtnListener() {
        binding.btnConfirmPurchase.setOnSingleClickListener {
            // TODO 구매 요청 서버통신 이후
            BuyPushActivity.createIntent(this, viewModel.productId).apply {
                startActivity(this)
            }
        }
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
        ): Intent = Intent(context, BuyConfirmActivity::class.java).apply {
            putExtra(EXTRA_PRODUCT_ID, productId)
        }
    }
}