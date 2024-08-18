package co.orange.presentation.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import co.orange.core.base.BaseBottomSheet
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.setOverThousand
import co.orange.presentation.buy.progress.BuyProgressActivity
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
        initLikeBtnListener()
        initAdapter()
        setInterestCount()
        setOptionData()
        observeLikeState()
    }

    private fun initDetailViewBtnListener() {
        binding.btnDetailView.setOnSingleClickListener {
            if (viewModel.infoUrl.isNotEmpty()) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(viewModel.infoUrl)))
            }
        }
    }

    private fun initPurchaseBtnListener() {
        binding.btnPurchase.setOnSingleClickListener {
            // TODO 버튼 활성화 설정
            BuyProgressActivity.createIntent(
                requireContext(),
                viewModel.productId,
            ).apply {
                startActivity(this)
            }
        }
    }

    private fun initLikeBtnListener() {
        binding.btnLike.setOnSingleClickListener {
            viewModel.setLikeStateWithServer()
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
        viewModel.interestCount.setOverThousand()
    }

    private fun setOptionData() {
        adapter.addList(viewModel.optionList)
    }

    private fun observeLikeState() {
        viewModel.likeState.flowWithLifecycle(lifecycle).distinctUntilChanged().onEach { isLiked ->
            if (isLiked) {
                binding.btnLike.setImageResource(R.drawable.ic_like_yellow)
            } else {
                binding.btnLike.setImageResource(R.drawable.ic_like_black_unselected)
            }
            binding.tvOptionLike.text = viewModel.interestCount.setOverThousand()
        }.launchIn(lifecycleScope)
    }
}
