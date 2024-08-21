package co.orange.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.orange.core.state.UiState
import co.orange.domain.entity.response.SearchInfoModel
import co.orange.domain.entity.response.SearchResultModel
import co.orange.domain.repository.DeviceRepository
import co.orange.domain.repository.InterestRepository
import co.orange.domain.repository.SearchRepository
import co.orange.domain.repository.UserRepository
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
        private val userRepository: UserRepository,
        private val interestRepository: InterestRepository,
    ) : ViewModel() {
        private val _getSearchInfoState = MutableStateFlow<UiState<SearchInfoModel>>(UiState.Empty)
        val getSearchInfoState: StateFlow<UiState<SearchInfoModel>> = _getSearchInfoState

        private val _getSearchResultState = MutableStateFlow<UiState<SearchResultModel>>(UiState.Empty)
        val getSearchResultState: StateFlow<UiState<SearchResultModel>> = _getSearchResultState

        private val _itemLikePlusState = MutableStateFlow<UiState<Int>>(UiState.Empty)
        val itemLikePlusState: StateFlow<UiState<Int>> = _itemLikePlusState

        private val _itemLikeMinusState = MutableStateFlow<UiState<Int>>(UiState.Empty)
        val itemLikeMinusState: StateFlow<UiState<Int>> = _itemLikeMinusState

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

        fun setLikeStateWithServer(
            productId: String,
            isInterested: Boolean,
            position: Int,
        ) {
            viewModelScope.launch {
                if (isInterested) {
                    interestRepository.deleteInterest(productId)
                        .onSuccess {
                            _itemLikeMinusState.value = UiState.Success(position)
                        }
                        .onFailure {
                            _itemLikeMinusState.value = UiState.Failure(it.message.orEmpty())
                        }
                } else {
                    interestRepository.postInterest(productId)
                        .onSuccess {
                            _itemLikePlusState.value = UiState.Success(position)
                        }
                        .onFailure {
                            _itemLikePlusState.value = UiState.Failure(it.message.orEmpty())
                        }
                }
            }
        }

        fun resetLikeState() {
            _itemLikePlusState.value = UiState.Empty
            _itemLikeMinusState.value = UiState.Empty
        }

        fun getUserLogined() = userRepository.getAccessToken().isNotEmpty()
    }
