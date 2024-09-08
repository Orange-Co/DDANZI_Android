package co.orange.presentation.sell.finished

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import co.orange.core.base.BaseActivity
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.setPriceForm
import co.orange.presentation.main.MainActivity
import co.orange.presentation.sell.info.SellInfoActivity
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import kr.genti.presentation.databinding.ActivitySellFinishedBinding
import kr.genti.presentation.R as featureR

@AndroidEntryPoint
class SellFinishedActivity :
    BaseActivity<ActivitySellFinishedBinding>(featureR.layout.activity_sell_finished) {
    private val viewModel by viewModels<SellFinishedViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initReturnBtnListener()
        initDetailBtnListener()
        setUiWithIntent()
    }

    private fun initReturnBtnListener() {
        with(binding) {
            btnExit.setOnSingleClickListener { returnToMainActivity() }
            btnSellMore.setOnSingleClickListener { returnToMainActivity() }
        }
    }

    private fun returnToMainActivity() {
        Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(this)
        }
    }

    private fun initDetailBtnListener() {
        binding.btnProductDetail.setOnSingleClickListener {
            SellInfoActivity.createIntent(this, viewModel.itemId).apply {
                startActivity(this)
            }
        }
    }

    private fun setUiWithIntent() {
        with(binding) {
            intent.getStringExtra(EXTRA_ITEM_ID)?.let { viewModel.itemId = it }
            intent.getStringExtra(EXTRA_PRODUCT_NAME)?.let { tvFinishedItemName.text = it }
            intent.getStringExtra(EXTRA_PRODUCT_IMAGE)?.let { ivFinishedItem.load(it) }
            tvFinishedItemPrice.text = intent.getIntExtra(EXTRA_SALE_PRICE, 0).setPriceForm()
        }
    }

    companion object {
        private const val EXTRA_ITEM_ID = "EXTRA_ITEM_ID"
        private const val EXTRA_PRODUCT_NAME = "EXTRA_PRODUCT_NAME"
        private const val EXTRA_PRODUCT_IMAGE = "EXTRA_PRODUCT_IMAGE"
        private const val EXTRA_SALE_PRICE = "EXTRA_SALE_PRICE"

        @JvmStatic
        fun createIntent(
            context: Context,
            itemId: String,
            productName: String,
            productImage: String,
            salePrice: Int,
        ): Intent =
            Intent(context, SellFinishedActivity::class.java).apply {
                putExtra(EXTRA_ITEM_ID, itemId)
                putExtra(EXTRA_PRODUCT_NAME, productName)
                putExtra(EXTRA_PRODUCT_IMAGE, productImage)
                putExtra(EXTRA_SALE_PRICE, salePrice)
            }
    }
}
