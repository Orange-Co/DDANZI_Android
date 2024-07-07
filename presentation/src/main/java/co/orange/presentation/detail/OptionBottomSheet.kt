package co.orange.presentation.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import co.orange.presentation.buy.confirm.BuyConfirmActivity
import kr.genti.core.base.BaseBottomSheet
import kr.genti.core.extension.setOnSingleClickListener
import kr.genti.core.extension.setOverThousand
import kr.genti.presentation.R
import kr.genti.presentation.databinding.BottomSheetOptionBinding

class OptionBottomSheet :
    BaseBottomSheet<BottomSheetOptionBinding>(R.layout.bottom_sheet_option) {
    private val viewModel by activityViewModels<DetailViewModel>()

    private var _adapter: OptionAdapter? = null
    val adapter
        get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(R.color.transparent)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
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
                viewModel.productId,
            ).apply {
                startActivity(this)
            }
        }
    }

    private fun initAdapter() {
        _adapter =
            OptionAdapter(
                itemClick = ::initItemClickListener,
            )
        binding.rvOption.adapter = adapter
    }

    private fun initItemClickListener(
        position: Int,
        optionId: Long,
        optionDetailId: Long,
    ) {
        // TODO 옵션 저장
    }

    private fun setInterestCount() {
        viewModel.mockProduct.interestCount.setOverThousand()
    }

    private fun setOptionData() {
        adapter.addList(viewModel.mockProduct.optionList)
    }
}
