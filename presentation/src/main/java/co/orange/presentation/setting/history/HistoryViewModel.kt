package co.orange.presentation.setting.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.orange.core.state.UiState
import co.orange.domain.entity.response.ProfileInterestModel
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

        private val _getInterestListState =
            MutableStateFlow<UiState<ProfileInterestModel>>(UiState.Empty)
        val getInterestListState: StateFlow<UiState<ProfileInterestModel>> = _getInterestListState

        fun getInterestListFromServer() {
            _getInterestListState.value = UiState.Loading
            viewModelScope.launch {
                profileRepository.getInterestList()
                    .onSuccess {
                        _getInterestListState.value = UiState.Success(it)
                    }
                    .onFailure {
                        _getInterestListState.value = UiState.Failure(it.message.toString())
                    }
            }
        }
    }
