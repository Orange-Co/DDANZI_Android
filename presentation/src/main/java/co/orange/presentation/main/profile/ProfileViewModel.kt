package co.orange.presentation.main.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.orange.core.state.UiState
import co.orange.domain.entity.response.ProfileNicknameModel
import co.orange.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
    @Inject
    constructor(
        private val profileRepository: ProfileRepository,
    ) : ViewModel() {
        private val _getNicknameState = MutableStateFlow<UiState<ProfileNicknameModel>>(UiState.Empty)
        val getNicknameState: StateFlow<UiState<ProfileNicknameModel>> = _getNicknameState

        fun getNicknameFromServer() {
            viewModelScope.launch {
                profileRepository.getNickname()
                    .onSuccess {
                        _getNicknameState.value = UiState.Success(it)
                    }
                    .onFailure {
                        _getNicknameState.value = UiState.Failure(it.message.toString())
                    }
            }
        }
    }
