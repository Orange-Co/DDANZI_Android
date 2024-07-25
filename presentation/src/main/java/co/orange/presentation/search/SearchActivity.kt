package co.orange.presentation.search

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import co.orange.core.base.BaseActivity
import co.orange.core.extension.initFocusWithKeyboard
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.stringOf
import co.orange.core.extension.toast
import co.orange.core.state.UiState
import co.orange.domain.entity.response.ProductModel
import co.orange.presentation.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivitySearchBinding

@AndroidEntryPoint
class SearchActivity :
    BaseActivity<ActivitySearchBinding>(R.layout.activity_search) {
    private val viewModel by viewModels<SearchViewModel>()

    private var _keywordAdapter: SearchWordAdapter? = null
    val keywordAdapter
        get() = requireNotNull(_keywordAdapter) { getString(R.string.adapter_not_initialized_error_msg) }

    private var _recentAdapter: SearchItemAdapter? = null
    val recentAdapter
        get() = requireNotNull(_recentAdapter) { getString(R.string.adapter_not_initialized_error_msg) }

    private var _resultAdapter: SearchItemAdapter? = null
    val resultAdapter
        get() = requireNotNull(_resultAdapter) { getString(R.string.adapter_not_initialized_error_msg) }

    private var searchJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initFocus()
        initBackBtnListener()
        initEmptyBtnListener()
        initKeywordAdapter()
        initRecentAdapter()
        initResultAdapter()
        setDebounceSearch()
        setKeywordList()
        setRecentList()
        observeGetSearchResultState()
    }

    private fun initFocus() {
        initFocusWithKeyboard(binding.etSearch)
    }

    private fun initBackBtnListener() {
        binding.btnBack.setOnSingleClickListener { finish() }
    }

    private fun initEmptyBtnListener() {
        binding.btnEmpty.setOnSingleClickListener {
            binding.etSearch.setText("")
        }
    }

    private fun initKeywordAdapter() {
        _keywordAdapter =
            SearchWordAdapter(
                keywordClick = ::initKeywordClickListener,
            )
        binding.rvSearchWord.adapter = keywordAdapter
    }

    private fun initKeywordClickListener(keyword: String) {
        binding.etSearch.setText(keyword)
    }

    private fun initRecentAdapter() {
        _recentAdapter =
            SearchItemAdapter(
                itemClick = ::initItemClickListener,
            )
        binding.rvSearchRecent.adapter = recentAdapter
    }

    private fun initResultAdapter() {
        _resultAdapter =
            SearchItemAdapter(
                itemClick = ::initItemClickListener,
            )
        binding.rvSearchResult.adapter = resultAdapter
    }

    private fun initItemClickListener(item: ProductModel) {
        DetailActivity.createIntent(
            this,
            item.productId,
        )
            .apply { startActivity(this) }
    }

    private fun setDebounceSearch() {
        binding.etSearch.doAfterTextChanged { text ->
            searchJob?.cancel()
            resultAdapter.submitList(listOf())
            binding.layoutBeforeSearch.isVisible = text.isNullOrBlank()
            if (!text.isNullOrBlank()) {
                searchJob =
                    lifecycleScope.launch {
                        delay(DEBOUNCE_TIME)
                        text.toString().let { text ->
                            binding.layoutAfterSearch.isVisible = true
                            binding.tvSearchedText.text = getString(R.string.add_quotation, text)
                            viewModel.getSearchResultFromServer(text)
                        }
                    }
            } else {
                binding.layoutAfterSearch.isVisible = false
            }
        }
    }

    private fun setKeywordList() {
        keywordAdapter.addList(viewModel.mockSearchModel.topSearchedList)
    }

    private fun setRecentList() {
        recentAdapter.addList(viewModel.mockSearchModel.recentViewedList)
    }

    private fun observeGetSearchResultState() {
        viewModel.getSearchResultState.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { state ->
                when (state) {
                    is UiState.Success -> {
                        resultAdapter.addList(state.data.searchedProductList)
                    }

                    is UiState.Failure -> toast(stringOf(R.string.error_msg))
                    else -> return@onEach
                }
            }.launchIn(lifecycleScope)
    }

    override fun onDestroy() {
        super.onDestroy()
        _keywordAdapter = null
        _recentAdapter = null
        _resultAdapter = null
    }

    companion object {
        const val DEBOUNCE_TIME = 500L
    }
}
