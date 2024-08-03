package co.orange.presentation.auth.phone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.orange.domain.repository.IamportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PhoneViewModel
    @Inject
    constructor(
        private val iamportRepository: IamportRepository,
    ) : ViewModel() {
        var certificatedUid: String = ""

        private val _getIamportTokenResult = MutableSharedFlow<Boolean>()
        val getIamportTokenResult: SharedFlow<Boolean> = _getIamportTokenResult

        private val _getIamportCertificationResult = MutableSharedFlow<Boolean>()
        val getIamportCertificationResult: SharedFlow<Boolean> = _getIamportCertificationResult

        fun postToGetIamportTokenFromServer() {
            viewModelScope.launch {
                iamportRepository.postToGetIamportToken()
                    .onSuccess {
                        if (it?.accessToken != null && certificatedUid.isNotBlank()) {
                            postToGetCertificationDataFromServer(it.accessToken)
                        } else {
                            _getIamportTokenResult.emit(false)
                        }
                    }
                    .onFailure {
                        _getIamportTokenResult.emit(false)
                    }
            }
        }

        private fun postToGetCertificationDataFromServer(accessToken: String) {
            viewModelScope.launch {
                iamportRepository.postToGetCertificationData(accessToken, certificatedUid)
                    .onSuccess {
                        if (it != null) {
                            Timber.tag("okhttp").d(it.toString())
                            _getIamportCertificationResult.emit(true)
                        } else {
                            _getIamportCertificationResult.emit(false)
                        }
                    }
                    .onFailure {
                        _getIamportCertificationResult.emit(false)
                    }
            }
        }
    }
