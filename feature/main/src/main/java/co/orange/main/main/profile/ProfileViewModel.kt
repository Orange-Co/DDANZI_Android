package co.orange.main.main.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.orange.core.state.UiState
import co.orange.domain.entity.response.NicknameModel
import co.orange.domain.repository.ProfileRepository
import co.orange.domain.repository.UserRepository
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
        private val userRepository: UserRepository,
    ) : ViewModel() {
        private val _getNicknameState = MutableStateFlow<UiState<NicknameModel>>(UiState.Empty)
        val getNicknameState: StateFlow<UiState<NicknameModel>> = _getNicknameState

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

        fun getUserLogined() = userRepository.getUserRegistered()
    }
