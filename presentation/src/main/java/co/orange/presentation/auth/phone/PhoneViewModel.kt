package co.orange.presentation.auth.phone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.orange.domain.repository.IamportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
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
                            getCertificationDataFromServer(it.accessToken)
                        } else {
                            _getIamportTokenResult.emit(false)
                        }
                    }
                    .onFailure {
                        _getIamportTokenResult.emit(false)
                    }
            }
        }

        private fun getCertificationDataFromServer(accessToken: String) {
            viewModelScope.launch {
                iamportRepository.getIamportCertificationData(accessToken, certificatedUid)
                    .onSuccess {
                        if (it != null) {
                            _getIamportCertificationResult.emit(true)
                            val name = it.name // "김상호" 형태
                            val phone = it.phone // "01032590327" 형태
                            val birth = it.birthday // "2000-03-27" 형태
                            val sex = it.gender // "male" 형태
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
