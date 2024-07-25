package co.orange.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.orange.core.state.UiState
import co.orange.domain.entity.response.SearchInfoModel
import co.orange.domain.entity.response.SearchResultModel
import co.orange.domain.repository.DeviceRepository
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
        private val deviceRepository: DeviceRepository,
    ) : ViewModel() {
        private val _getSearchInfoState = MutableStateFlow<UiState<SearchInfoModel>>(UiState.Empty)
        val getSearchInfoState: StateFlow<UiState<SearchInfoModel>> = _getSearchInfoState

        private val _getSearchResultState = MutableStateFlow<UiState<SearchResultModel>>(UiState.Empty)
        val getSearchResultState: StateFlow<UiState<SearchResultModel>> = _getSearchResultState

        init {
            getSearchInfoFromServer()
        }

        private fun getSearchInfoFromServer() {
            _getSearchInfoState.value = UiState.Loading
            viewModelScope.launch {
                deviceRepository.getSearchInfo()
                    .onSuccess {
                        _getSearchInfoState.value = UiState.Success(it)
                    }
                    .onFailure {
                        _getSearchInfoState.value = UiState.Failure(it.message.toString())
                    }
            }
        }

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
    }
