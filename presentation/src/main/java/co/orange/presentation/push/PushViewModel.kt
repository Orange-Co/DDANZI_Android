package co.orange.presentation.push

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.orange.domain.repository.SettingRepository
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PushViewModel
    @Inject
    constructor(
        private val settingRepository: SettingRepository,
    ) : ViewModel() {
        private val _getDeviceTokenResult = MutableSharedFlow<Boolean>()
        val getDeviceTokenResult: SharedFlow<Boolean> = _getDeviceTokenResult

        private fun getDeviceToken() {
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                viewModelScope.launch {
                    if (task.isSuccessful) {
                        //
                    } else {
                        _getDeviceTokenResult.emit(false)
                    }
                }
            }
        }
    }
