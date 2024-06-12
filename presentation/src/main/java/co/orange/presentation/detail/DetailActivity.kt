package co.orange.presentation.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import co.orange.domain.entity.response.ProductDetailModel
import co.orange.presentation.main.home.HomeProductViewHolder.Companion.OVER_999
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import kr.genti.core.base.BaseActivity
import kr.genti.core.extension.setOnSingleClickListener
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivityDetailBinding

@AndroidEntryPoint
class DetailActivity : BaseActivity<ActivityDetailBinding>(R.layout.activity_detail) {
    private val viewModel by viewModels<DetailViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBackBtnListener()
        initDetailViewBtnListener()
        initLikeBtnListener()
        initPurchaseBtnListener()
        getIntentInfo()
        setProduct(viewModel.mockProduct)

    }

    private fun initBackBtnListener() {
        binding.btnBack.setOnSingleClickListener { finish() }
        binding.btnHome.setOnSingleClickListener { finish() }
    }

    private fun initDetailViewBtnListener() {
        // TODO
        binding.btnDetailView.setOnSingleClickListener { }
    }

    private fun initLikeBtnListener() {
        // TODO
        binding.btnLike.setOnSingleClickListener { }
    }

    private fun initPurchaseBtnListener() {
        // TODO
        binding.btnPurchase.setOnSingleClickListener { }
    }

    private fun getIntentInfo() {
        with(binding) {
            ivDetailProduct.load(intent.getStringExtra(EXTRA_PRODUCT_URL))
            tvDetailRealPrice.text = intent.getIntExtra(EXTRA_ORIGIN_PRICE, 0).toString()
            tvDetailNowPrice.text = intent.getIntExtra(EXTRA_SALE_PRICE, 0).toString()
        }
    }

    private fun setProduct(item: ProductDetailModel) {
        with(binding) {
            tvDetailTitle.text = item.name
            chipsDetailCategory.text = item.category
            chipsDetailOption.isVisible = item.isOptionExist
            chipsDetailImminent.isVisible = item.isImminent
            tvDetailDiscountPercent.text = item.discountRate.toString()
            tvDetailStockCount.text = item.stockCount.toString()
            tvDetailLike.text = viewModel.mockProduct.interestCount.toString()
            if (item.interestCount < 1000) {
                tvDetailLike.text = item.interestCount.toString()
            } else {
                tvDetailLike.text = OVER_999
            }
        }
    }

    companion object {
        private const val EXTRA_PRODUCT_URL = "EXTRA_PRODUCT_URL"
        private const val EXTRA_ORIGIN_PRICE = "EXTRA_ORIGIN_PRICE"
        private const val EXTRA_SALE_PRICE = "EXTRA_SALE_PRICE"

        @JvmStatic
        fun createIntent(
            context: Context,
            productUrl: String,
            originPrice: Int,
            salePrice: Int
        ): Intent = Intent(context, DetailActivity::class.java).apply {
            putExtra(EXTRA_PRODUCT_URL, productUrl)
            putExtra(EXTRA_ORIGIN_PRICE, originPrice)
            putExtra(EXTRA_SALE_PRICE, salePrice)
        }
    }
}