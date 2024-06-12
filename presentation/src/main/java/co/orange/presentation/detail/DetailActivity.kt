package co.orange.presentation.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
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
        setProduct()

    }

    private fun initBackBtnListener() {
        binding.btnBack.setOnSingleClickListener { finish() }
        binding.btnHome.setOnSingleClickListener { finish() }
    }

    private fun initDetailViewBtnListener() {
        // TODO
        binding.btnDetailView.setOnSingleClickListener {  }
    }

    private fun initLikeBtnListener() {
        // TODO
        binding.btnLike.setOnSingleClickListener {  }
    }

    private fun initPurchaseBtnListener() {
        // TODO
        binding.btnPurchase.setOnSingleClickListener {  }
    }

    private fun setProduct() {
        with(binding) {
            tvDetailTitle.text = viewModel.mockProduct.name
            chipsDetailCategory.text = viewModel.mockProduct.category
            chipsDetailOption.isVisible = viewModel.mockProduct.isOptionExist
            chipsDetailImminent.isVisible = viewModel.mockProduct.isImminent
            tvDetailDiscountPercent.text = viewModel.mockProduct.discountRate.toString()
            tvDetailStockCount.text = viewModel.mockProduct.stockCount.toString()
            tvDetailLike.text = viewModel.mockProduct.interestCount.toString()
        }
    }
}