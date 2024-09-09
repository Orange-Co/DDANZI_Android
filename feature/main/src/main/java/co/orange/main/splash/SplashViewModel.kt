package co.orange.main.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.orange.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) : ViewModel() {
        private val _isServerAvailable = MutableSharedFlow<Boolean>()
        val isServerAvailable: SharedFlow<Boolean> = _isServerAvailable

        fun getServerStatusToCheckAvailable() {
            viewModelScope.launch {
                authRepository.getServerStatus()
                    .onSuccess { isAvailable ->
                        if (isAvailable) {
                            _isServerAvailable.emit(true)
                        } else {
                            _isServerAvailable.emit(false)
                        }
                    }
                    .onFailure {
                        _isServerAvailable.emit(false)
                    }
            }
        }
    }
