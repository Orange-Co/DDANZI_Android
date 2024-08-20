package co.orange.presentation.main.profile.history

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import co.orange.core.base.BaseActivity
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.stringOf
import co.orange.core.extension.toast
import co.orange.core.state.UiState
import co.orange.presentation.buy.finished.BuyFinishedActivity
import co.orange.presentation.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivityHistoryBinding

@AndroidEntryPoint
class HistoryActivity : BaseActivity<ActivityHistoryBinding>(R.layout.activity_history) {
    val viewModel by viewModels<HistoryViewModel>()

    private var _interestAdapter: HistoryInterestAdapter? = null
    val interestAdapter
        get() = requireNotNull(_interestAdapter) { getString(R.string.adapter_not_initialized_error_msg) }

    private var _buyAdapter: HistoryBuyAdapter? = null
    val buyAdapter
        get() = requireNotNull(_buyAdapter) { getString(R.string.adapter_not_initialized_error_msg) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBackBtnListener()
        initAdapter()
        observeGetBuyListState()
        observeGetInterestListState()
    }

    override fun onResume() {
        super.onResume()

        getTypeFromIntent()
    }

    private fun initBackBtnListener() {
        binding.btnBack.setOnSingleClickListener { finish() }
    }

    private fun initAdapter() {
        _buyAdapter =
            HistoryBuyAdapter { orderId ->
                BuyFinishedActivity.createIntent(this, orderId).apply {
                    startActivity(this)
                }
            }
        _interestAdapter =
            HistoryInterestAdapter { productId ->
                DetailActivity.createIntent(this, productId).apply {
                    startActivity(this)
                }
            }
        binding.rvHistory.adapter = interestAdapter
    }

    private fun getTypeFromIntent() {
        if (viewModel.currentType == -1) viewModel.currentType = intent.getIntExtra(EXTRA_TYPE, -1)
        setUiWithType()
    }

    private fun setUiWithType() {
        when (viewModel.currentType) {
            TYPE_BUY -> {
                binding.tvHistoryTitle.text = stringOf(R.string.profile_history_buy_title)
                viewModel.getBuyListFromServer()
            }

            TYPE_SELL -> {
                binding.tvHistoryTitle.text = stringOf(R.string.profile_history_sell_title)
            }

            TYPE_INTEREST -> {
                binding.tvHistoryTitle.text = stringOf(R.string.profile_history_interest_title)
                viewModel.getInterestListFromServer()
            }

            else -> return
        }
    }

    private fun observeGetBuyListState() {
        viewModel.getBuyListState.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { state ->
                when (state) {
                    is UiState.Success -> {
                        with(binding) {
                            tvHistoryCount.text =
                                getString(R.string.profile_history_count, state.data.totalCount)
                            tvHistoryEmpty.text = getString(R.string.profile_history_buy_empty)
                            layoutHistoryEmpty.isVisible = state.data.orderProductList.isEmpty()
                            rvHistory.adapter = buyAdapter
                        }
                        buyAdapter.submitList(state.data.orderProductList)
                    }

                    is UiState.Failure -> toast(stringOf(R.string.error_msg))
                    else -> return@onEach
                }
            }.launchIn(lifecycleScope)
    }

    private fun observeGetInterestListState() {
        viewModel.getInterestListState.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { state ->
                when (state) {
                    is UiState.Success -> {
                        with(binding) {
                            tvHistoryCount.text =
                                getString(R.string.profile_history_count, state.data.totalCount)
                            tvHistoryEmpty.text = getString(R.string.profile_history_interest_empty)
                            layoutHistoryEmpty.isVisible = state.data.productList.isEmpty()
                            rvHistory.adapter = interestAdapter
                        }
                        interestAdapter.submitList(state.data.productList)
                    }

                    is UiState.Failure -> toast(stringOf(R.string.error_msg))
                    else -> return@onEach
                }
            }.launchIn(lifecycleScope)
    }

    override fun onDestroy() {
        super.onDestroy()

        _buyAdapter = null
        _interestAdapter = null
    }

    companion object {
        private const val EXTRA_TYPE = "EXTRA_TYPE"

        const val TYPE_BUY = 0
        const val TYPE_SELL = 1
        const val TYPE_INTEREST = 2

        @JvmStatic
        fun createIntent(
            context: Context,
            type: Int,
        ): Intent =
            Intent(context, HistoryActivity::class.java).apply {
                putExtra(EXTRA_TYPE, type)
            }
    }
}
