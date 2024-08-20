package co.orange.presentation.main.profile.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.orange.core.state.UiState
import co.orange.domain.entity.response.HistoryBuyModel
import co.orange.domain.entity.response.HistoryInterestModel
import co.orange.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel
    @Inject
    constructor(
        private val profileRepository: ProfileRepository,
    ) : ViewModel() {
        var currentType: Int = -1

        private val _getBuyListState =
            MutableStateFlow<UiState<HistoryBuyModel>>(UiState.Empty)
        val getBuyListState: StateFlow<UiState<HistoryBuyModel>> = _getBuyListState

        private val _getInterestListState =
            MutableStateFlow<UiState<HistoryInterestModel>>(UiState.Empty)
        val getInterestListState: StateFlow<UiState<HistoryInterestModel>> = _getInterestListState

        fun getBuyListFromServer() {
            _getBuyListState.value = UiState.Loading
            viewModelScope.launch {
                profileRepository.getBuyHistory()
                    .onSuccess {
                        _getBuyListState.value = UiState.Success(it)
                    }
                    .onFailure {
                        _getBuyListState.value = UiState.Failure(it.message.toString())
                    }
            }
        }

        fun getInterestListFromServer() {
            _getInterestListState.value = UiState.Loading
            viewModelScope.launch {
                profileRepository.getInterestHistory()
                    .onSuccess {
                        _getInterestListState.value = UiState.Success(it)
                    }
                    .onFailure {
                        _getInterestListState.value = UiState.Failure(it.message.toString())
                    }
            }
        }
    }
