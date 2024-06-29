package co.orange.presentation.sell.confirm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import co.orange.domain.entity.response.SellProductModel
import co.orange.presentation.sell.push.SellPushActivity
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import kr.genti.core.base.BaseActivity
import kr.genti.core.extension.setNumberForm
import kr.genti.core.extension.setOnSingleClickListener
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivitySellConfirmBinding

@AndroidEntryPoint
class SellConfirmActivity :
    BaseActivity<ActivitySellConfirmBinding>(R.layout.activity_sell_confirm) {
    private val viewModel by viewModels<SellConfirmViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initExitBtnListener()
        initTermBtnListener()
        initConfirmBtnListener()
        initDateBtnListener()
        getIntentInfo()
        setIntentUi(viewModel.mockSellInfo)
    }

    private fun initExitBtnListener() {
        binding.btnExit.setOnSingleClickListener { finish() }
    }

    private fun initTermBtnListener() {
        // TODO
        binding.btnTermAll.setOnSingleClickListener { }
        binding.btnTermFirst.setOnSingleClickListener { }
        binding.btnTermSecond.setOnSingleClickListener { }
        binding.btnTermThird.setOnSingleClickListener { }
    }

    private fun initConfirmBtnListener() {
        binding.btnConfirmSell.setOnSingleClickListener {
            // TODO 구매 요청 서버통신 이후
            SellPushActivity.createIntent(this, -1).apply {
                startActivity(this)
            }
        }
    }

    private fun initDateBtnListener() {
        // TODO 데이트피커 구현
    }

    private fun getIntentInfo() {
        viewModel.productId = intent.getLongExtra(EXTRA_PRODUCT_ID, -1)
    }

    private fun setIntentUi(item: SellProductModel) {
        with(binding) {
            tvSellInfoName.text = item.productName
            tvSellInfoOriginPrice.text = item.originPrice.setNumberForm()
            tvSellInfoSellPrice.text = item.salePrice.setNumberForm()
            // TODO : intent 수정
            ivSellProduct.load(R.drawable.mock_img_product)
            tvSellDate.text = "2024.06.28"
        }
    }

    companion object {
        private const val EXTRA_PRODUCT_ID = "EXTRA_PRODUCT_ID"

        @JvmStatic
        fun createIntent(
            context: Context,
            productId: Long,
        ): Intent =
            Intent(context, SellConfirmActivity::class.java).apply {
                putExtra(EXTRA_PRODUCT_ID, productId)
            }
    }
}
