package co.orange.main.search

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import co.orange.core.R
import co.orange.core.base.BaseActivity
import co.orange.core.extension.initFocusWithKeyboard
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.stringOf
import co.orange.core.extension.toast
import co.orange.core.navigation.NavigationManager
import co.orange.core.state.UiState
import co.orange.domain.entity.response.ProductModel
import co.orange.main.databinding.ActivitySearchBinding
import co.orange.main.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import co.orange.main.R as featureR

@AndroidEntryPoint
class SearchActivity :
    BaseActivity<ActivitySearchBinding>(featureR.layout.activity_search) {
    @Inject
    lateinit var navigationManager: NavigationManager

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
        observeGetSearchViewInfoState()
        observeGetSearchResultState()
        observeItemLikePlusState()
        observeItemLikeMinusState()
    }

    override fun onResume() {
        super.onResume()

        getSearchData()
    }

    private fun getSearchData() {
        with(viewModel) {
            if (currentKeyword.isEmpty()) {
                getSearchInfoFromServer()
            } else {
                getSearchResultFromServer(currentKeyword)
            }
        }
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
                likeClick = ::initLikeClickListener,
            )
        binding.rvSearchRecent.adapter = recentAdapter
    }

    private fun initResultAdapter() {
        _resultAdapter =
            SearchItemAdapter(
                itemClick = ::initItemClickListener,
                likeClick = ::initLikeClickListener,
            )
        binding.rvSearchResult.adapter = resultAdapter
    }

    private fun initItemClickListener(item: ProductModel) {
        startActivity(DetailActivity.createIntent(this, item.productId))
    }

    private fun initLikeClickListener(
        productId: String,
        isInterested: Boolean,
        position: Int,
    ) {
        if (!viewModel.getUserLogined()) {
            navigationManager.toLoginView(this, "like")
            return
        }
        viewModel.setLikeStateWithServer(productId, isInterested, position)
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

    private fun observeGetSearchViewInfoState() {
        viewModel.getSearchInfoState.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { state ->
                when (state) {
                    is UiState.Success -> {
                        keywordAdapter.submitList(state.data.topSearchedList)
                        recentAdapter.submitList(state.data.recentlyViewedList)
                        binding.layoutRecentEmpty.isVisible =
                            state.data.recentlyViewedList.isEmpty()
                    }

                    is UiState.Failure -> toast(stringOf(R.string.error_msg))
                    else -> return@onEach
                }
            }.launchIn(lifecycleScope)
    }

    private fun observeGetSearchResultState() {
        viewModel.getSearchResultState.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { state ->
                when (state) {
                    is UiState.Success -> {
                        resultAdapter.submitList(state.data.searchedProductList)
                        binding.layoutResultEmpty.isVisible =
                            state.data.searchedProductList.isEmpty()
                    }

                    is UiState.Failure -> toast(stringOf(R.string.error_msg))
                    else -> return@onEach
                }
            }.launchIn(lifecycleScope)
    }

    private fun observeItemLikePlusState() {
        viewModel.itemLikePlusState.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { state ->
                when (state) {
                    is UiState.Success -> {
                        if (viewModel.currentKeyword.isEmpty()) {
                            recentAdapter.plusItemLike(state.data)
                        } else {
                            resultAdapter.plusItemLike(state.data)
                        }
//                        with(binding) {
//                            lottieLike.isVisible = true
//                            lottieLike.playAnimation()
//                            delay(500)
//                            lottieLike.isVisible = false
//                        }
                    }

                    is UiState.Failure -> toast(stringOf(R.string.error_msg))
                    else -> return@onEach
                }
                viewModel.resetLikeState()
            }.launchIn(lifecycleScope)
    }

    private fun observeItemLikeMinusState() {
        viewModel.itemLikeMinusState.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { state ->
                when (state) {
                    is UiState.Success -> {
                        if (viewModel.currentKeyword.isEmpty()) {
                            recentAdapter.minusItemLike(state.data)
                        } else {
                            resultAdapter.minusItemLike(state.data)
                        }
                    }

                    is UiState.Failure -> toast(stringOf(R.string.error_msg))
                    else -> return@onEach
                }
                viewModel.resetLikeState()
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
