package co.orange.main.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import co.orange.core.R
import co.orange.core.amplitude.AmplitudeManager
import co.orange.core.base.BaseBottomSheet
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.setOverThousand
import co.orange.core.navigation.NavigationManager
import co.orange.main.databinding.BottomSheetOptionBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import co.orange.main.R as featureR

@AndroidEntryPoint
class OptionBottomSheet :
    BaseBottomSheet<BottomSheetOptionBinding>(featureR.layout.bottom_sheet_option) {
    @Inject
    lateinit var navigationManager: NavigationManager

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

        AmplitudeManager.trackEvent("view_option")
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
            AmplitudeManager.trackEvent("click_option_next")
            navigationManager.toBuyProgressView(
                requireContext(),
                viewModel.productId,
                viewModel.selectedOptionList,
            )
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
        optionDetailId: Long,
    ) {
        if (position < viewModel.selectedOptionList.size) {
            viewModel.selectedOptionList[position] = optionDetailId.toInt()
        }
        binding.btnPurchase.isEnabled = !viewModel.selectedOptionList.contains(-1)
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
