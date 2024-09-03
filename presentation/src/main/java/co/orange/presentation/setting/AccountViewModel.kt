package co.orange.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.orange.core.state.UiState
import co.orange.domain.repository.SettingRepository
import co.orange.domain.repository.UserRepository
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel
    @Inject
    constructor(
        private val settingRepository: SettingRepository,
        private val userRepository: UserRepository,
    ) : ViewModel() {
        private val _kakaoLogoutResult = MutableSharedFlow<Boolean>()
        val kakaoLogoutResult: SharedFlow<Boolean> = _kakaoLogoutResult

        private val _userLogoutState = MutableStateFlow<UiState<Boolean>>(UiState.Empty)
        val userLogoutState: StateFlow<UiState<Boolean>> = _userLogoutState

        private val _kakaoQuitResult = MutableSharedFlow<Boolean>()
        val kakaoQuitResult: SharedFlow<Boolean> = _kakaoQuitResult

        private val _userQuitState = MutableStateFlow<UiState<String>>(UiState.Empty)
        val userQuitState: StateFlow<UiState<String>> = _userQuitState

        fun logoutKakaoAccount() {
            UserApiClient.instance.logout { error ->
                viewModelScope.launch {
                    if (error == null) {
                        logoutFromServer()
                    } else {
                        _kakaoLogoutResult.emit(false)
                    }
                }
            }
        }

        private fun logoutFromServer() {
            _userLogoutState.value = UiState.Loading
            viewModelScope.launch {
                settingRepository.postUserLogout()
                    .onSuccess {
                        userRepository.clearInfo()
                        _userLogoutState.value = UiState.Success(it)
                    }.onFailure {
                        _userLogoutState.value = UiState.Failure(it.message.toString())
                    }
            }
        }

        fun quitKakaoAccount() {
            UserApiClient.instance.unlink { error ->
                viewModelScope.launch {
                    if (error == null) {
                        quitFromServer()
                    } else {
                        _kakaoQuitResult.emit(false)
                    }
                }
            }
        }

        private fun quitFromServer() {
            _userQuitState.value = UiState.Loading
            viewModelScope.launch {
                settingRepository.deleteToUserQuit()
                    .onSuccess {
                        userRepository.clearInfo()
                        _userQuitState.value = UiState.Success(it.nickname)
                    }.onFailure {
                        _userQuitState.value = UiState.Failure(it.message.toString())
                    }
            }
        }
    }
