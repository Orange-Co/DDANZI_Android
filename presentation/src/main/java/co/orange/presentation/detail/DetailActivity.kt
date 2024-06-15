package co.orange.presentation.detail

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import co.orange.domain.entity.response.ProductDetailModel
import co.orange.presentation.main.home.HomeProductViewHolder.Companion.OVER_999
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import kr.genti.core.base.BaseActivity
import kr.genti.core.extension.breakLines
import kr.genti.core.extension.setNumberForm
import kr.genti.core.extension.setOnSingleClickListener
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivityDetailBinding

@AndroidEntryPoint
class DetailActivity : BaseActivity<ActivityDetailBinding>(R.layout.activity_detail) {
    private val viewModel by viewModels<DetailViewModel>()

    private var optionBottomSheet: OptionBottomSheet? = null

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
        binding.btnDetailView.setOnSingleClickListener {
            if (viewModel.mockProduct.infoUrl.isNotEmpty()) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(viewModel.mockProduct.infoUrl)))
            }
        }
    }

    private fun initLikeBtnListener() {
        // TODO
        binding.btnLike.setOnSingleClickListener { }
    }

    private fun initPurchaseBtnListener() {
        binding.btnPurchase.setOnSingleClickListener {
            optionBottomSheet = OptionBottomSheet()
            optionBottomSheet?.show(supportFragmentManager, BOTTOM_SHEET_OPTION)
        }
    }

    private fun getIntentInfo() {
        with(viewModel) {
            productId =intent.getLongExtra(EXTRA_PRODUCT_ID, -1)
            imageUrl = intent.getStringExtra(EXTRA_PRODUCT_URL).orEmpty()
            originPrice = intent.getIntExtra(EXTRA_ORIGIN_PRICE, 0)
            salePrice = intent.getIntExtra(EXTRA_SALE_PRICE, 0)
        }
        with(binding) {
            ivDetailProduct.load(viewModel.imageUrl)
            tvDetailRealPrice.apply {
                text = viewModel.originPrice.setNumberForm()
                setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG)
            }
            tvDetailNowPrice.text = viewModel.salePrice.setNumberForm()
        }
    }

    private fun setProduct(item: ProductDetailModel) {
        with(binding) {
            tvDetailTitle.text = item.name.breakLines()
            chipsDetailCategory.text = item.category
            chipsDetailOption.isVisible = item.isOptionExist
            chipsDetailImminent.isVisible = item.isImminent
            tvDetailDiscountRate.text = item.discountRate.toString()
            tvDetailStockCount.text = item.stockCount.toString()
            tvDetailLike.text = viewModel.mockProduct.interestCount.toString()
            if (item.interestCount < 1000) {
                tvDetailLike.text = item.interestCount.toString()
            } else {
                tvDetailLike.text = OVER_999
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        optionBottomSheet = null
    }

    companion object {
        private const val EXTRA_PRODUCT_ID = "EXTRA_PRODUCT_ID"
        private const val EXTRA_PRODUCT_URL = "EXTRA_PRODUCT_URL"
        private const val EXTRA_ORIGIN_PRICE = "EXTRA_ORIGIN_PRICE"
        private const val EXTRA_SALE_PRICE = "EXTRA_SALE_PRICE"

        private const val BOTTOM_SHEET_OPTION = "BOTTOM_SHEET_OPTION"

        @JvmStatic
        fun createIntent(
            context: Context,
            productId: Long,
            productUrl: String,
            originPrice: Int,
            salePrice: Int,
        ): Intent = Intent(context, DetailActivity::class.java).apply {
            putExtra(EXTRA_PRODUCT_ID, productId)
            putExtra(EXTRA_PRODUCT_URL, productUrl)
            putExtra(EXTRA_ORIGIN_PRICE, originPrice)
            putExtra(EXTRA_SALE_PRICE, salePrice)
        }
    }
}