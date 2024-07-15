package co.orange.presentation.setting.history

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import co.orange.core.base.BaseActivity
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.stringOf
import co.orange.core.extension.toast
import co.orange.core.state.UiState
import co.orange.domain.entity.response.ProductModel
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

    private var _adapter: HistoryAdapter? = null
    val adapter
        get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBackBtnListener()
        initAdapter()
        getTypeFromIntent()
        observeGetInterestListState()
    }

    private fun initBackBtnListener() {
        binding.btnBack.setOnSingleClickListener { finish() }
    }

    private fun initAdapter() {
        _adapter =
            HistoryAdapter(
                itemClick = ::initItemClickListener,
            )
        binding.rvHistory.adapter = adapter
    }

    private fun initItemClickListener(item: ProductModel) {
        DetailActivity.createIntent(
            this,
            item.productId,
            item.imgUrl,
            item.originPrice,
            item.salePrice,
        )
            .apply { startActivity(this) }
    }

    private fun getTypeFromIntent() {
        viewModel.currentType = intent.getIntExtra(EXTRA_TYPE, -1)
        setUiWithType()
    }

    private fun setUiWithType() {
        when (viewModel.currentType) {
            TYPE_BUY -> {
                binding.tvHistoryTitle.text = stringOf(R.string.profile_history_buy_title)
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

    private fun observeGetInterestListState() {
        viewModel.getInterestListState.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { state ->
                when (state) {
                    is UiState.Success -> {
                        binding.tvHistoryCount.text =
                            getString(R.string.profile_history_count, state.data.totalCount)
                        adapter.addList(state.data.productList)
                    }

                    is UiState.Failure -> toast(stringOf(R.string.error_msg))
                    else -> return@onEach
                }
            }.launchIn(lifecycleScope)
    }

    override fun onDestroy() {
        super.onDestroy()
        _adapter = null
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
