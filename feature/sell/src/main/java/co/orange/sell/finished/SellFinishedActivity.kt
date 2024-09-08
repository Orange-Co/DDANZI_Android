package co.orange.sell.finished

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import co.orange.core.base.BaseActivity
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.setPriceForm
import co.orange.core.navigation.NavigationManager
import co.orange.sell.databinding.ActivitySellFinishedBinding
import co.orange.sell.info.SellInfoActivity
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import co.orange.sell.R as featureR

@AndroidEntryPoint
class SellFinishedActivity :
    BaseActivity<ActivitySellFinishedBinding>(featureR.layout.activity_sell_finished) {
    @Inject
    lateinit var navigationManager: NavigationManager

    private val viewModel by viewModels<SellFinishedViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initReturnBtnListener()
        initDetailBtnListener()
        setUiWithIntent()
    }

    private fun initReturnBtnListener() {
        with(binding) {
            btnExit.setOnSingleClickListener { navigationManager.toMainViewWIthClearing() }
            btnSellMore.setOnSingleClickListener { navigationManager.toMainViewWIthClearing() }
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
