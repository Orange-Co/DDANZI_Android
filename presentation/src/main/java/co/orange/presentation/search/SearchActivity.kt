package co.orange.presentation.search

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kr.genti.core.base.BaseActivity
import kr.genti.core.extension.setOnSingleClickListener
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

    private var searchJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initFocusToEditText()
        initBackBtnListener()
        initKeywordAdapter()
        initRecentAdapter()
        setDebounceSearch()
        setKeywordList()
        setRecentList()
    }

    private fun initFocusToEditText() {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        binding.etSearch.requestFocus()
        inputMethodManager.showSoftInput(
            binding.etSearch,
            InputMethodManager.SHOW_IMPLICIT,
        )
    }

    private fun initBackBtnListener() {
        binding.btnBack.setOnSingleClickListener { finish() }
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

    private fun initItemClickListener(id: Long) {
        //
    }

    private fun setDebounceSearch() {
        binding.etSearch.doAfterTextChanged { text ->
            searchJob?.cancel()
            with(binding) {
                layoutBeforeSearch.isVisible = text.isNullOrBlank()
                layoutAfterSearch.isVisible = !text.isNullOrBlank()
            }
            if (!text.isNullOrBlank()) {
                searchJob =
                    lifecycleScope.launch {
                        delay(DEBOUNCE_TIME)
                        text.toString().let { text ->
                            // viewModel.setFriendsListFromServer(text)
                        }
                    }
            }
        }
    }

    private fun setKeywordList() {
        keywordAdapter.addList(viewModel.mockSearchModel.topSearchedList)
    }

    private fun setRecentList() {
        recentAdapter.addList(viewModel.mockSearchModel.recentViewedList)
    }

    override fun onDestroy() {
        super.onDestroy()
        _keywordAdapter = null
        _recentAdapter = null
    }

    companion object {
        const val DEBOUNCE_TIME = 500L
    }
}
