package co.orange.presentation.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import co.orange.presentation.buy.confirm.BuyConfirmActivity
import co.orange.presentation.main.home.HomeProductViewHolder.Companion.OVER_999
import kr.genti.core.base.BaseBottomSheet
import kr.genti.core.extension.setOnSingleClickListener
import kr.genti.presentation.R
import kr.genti.presentation.databinding.BottomSheetOptionBinding

class OptionBottomSheet :
    BaseBottomSheet<BottomSheetOptionBinding>(R.layout.bottom_sheet_option) {

    private val viewModel by activityViewModels<DetailViewModel>()

    private var _adapter: OptionAdapter? = null
    private val adapter
        get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(R.color.transparent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDetailViewBtnListener()
        initPurchaseBtnListener()
        initAdapter()
        setInterestCount()
        setOptionData()
    }

    private fun initDetailViewBtnListener() {
        binding.btnDetailView.setOnSingleClickListener {
            if (viewModel.mockProduct.infoUrl.isNotEmpty()) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(viewModel.mockProduct.infoUrl)))
            }
        }
    }

    private fun initPurchaseBtnListener() {
        binding.btnPurchase.setOnSingleClickListener {
            // TODO 버튼 활성화 설정
            BuyConfirmActivity.createIntent(
                requireContext(),
                viewModel.imageUrl,
                viewModel.originPrice,
                viewModel.salePrice,
                viewModel.mockProduct.name
            ).apply {
                startActivity(this)
            }
        }
    }

    private fun initAdapter() {
        _adapter = OptionAdapter(
            itemClick = ::initItemClickListener,
        )
        binding.rvOption.adapter = adapter
    }

    private fun initItemClickListener(position: Int, optionId: Long) {
        // TODO
    }

    private fun setInterestCount() {
        binding.tvOptionLike.text = if (viewModel.mockProduct.interestCount < 1000) {
            viewModel.mockProduct.interestCount.toString()
        } else {
            OVER_999
        }
    }

    private fun setOptionData() {
        adapter.addList(viewModel.mockProduct.optionList)
    }
}