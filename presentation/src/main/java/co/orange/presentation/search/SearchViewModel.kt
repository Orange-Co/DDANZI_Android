package co.orange.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.orange.core.state.UiState
import co.orange.domain.entity.response.ProductModel
import co.orange.domain.entity.response.SearchModel
import co.orange.domain.entity.response.SearchResultModel
import co.orange.domain.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
    @Inject
    constructor(
        private val searchRepository: SearchRepository,
    ) : ViewModel() {
        private val _getSearchResultState = MutableStateFlow<UiState<SearchResultModel>>(UiState.Empty)
        val getSearchResultState: StateFlow<UiState<SearchResultModel>> = _getSearchResultState

        fun getSearchResultFromServer(keyword: String) {
            _getSearchResultState.value = UiState.Loading
            viewModelScope.launch {
                searchRepository.getSearchResult(keyword)
                    .onSuccess {
                        _getSearchResultState.value = UiState.Success(it)
                    }
                    .onFailure {
                        _getSearchResultState.value = UiState.Failure(it.message.toString())
                    }
            }
        }

        var mockSearchModel =
            SearchModel(
                listOf("향수", "이승준", "곰인형", "성년의 날 선물", "멀티비타민"),
                listOf(
                    ProductModel(
                        "1",
                        0,
                        "퓨어 오일 퍼퓸 10 ml 긴제목테스트트트트트",
                        "https://github.com/Marchbreeze/Marchbreeze/assets/97405341/cd2c0454-92b4-41e7-ae2f-319f83e2426f",
                        54000,
                        48900,
                        12,
                    ),
                    ProductModel(
                        "2",
                        0,
                        "퓨어 오일 퍼퓸 10 ml 긴제목테스트트트트트",
                        "https://github.com/Marchbreeze/Marchbreeze/assets/97405341/cd2c0454-92b4-41e7-ae2f-319f83e2426f",
                        54000,
                        48900,
                        34,
                    ),
                ),
            )
    }
