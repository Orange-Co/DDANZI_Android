package co.orange.presentation.auth.phone

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.orange.core.extension.toPhoneFrom
import co.orange.domain.repository.IamportRepository
import co.orange.domain.repository.UserRepository
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
        private val userRepository: UserRepository,
    ) : ViewModel() {
        var certificatedUid: String = ""

        var isTermAllSelected = MutableLiveData<Boolean>(false)
        var isTermPrivateSelected = MutableLiveData<Boolean>(false)
        var isTermServiceSelected = MutableLiveData<Boolean>(false)
        var isTermMarketingSelected = MutableLiveData<Boolean>(false)
        var isCompleted = MutableLiveData<Boolean>(false)

        private val _isSubmitClicked = MutableSharedFlow<Boolean>()
        val isSubmitClicked: SharedFlow<Boolean> = _isSubmitClicked

        private val _getIamportTokenResult = MutableSharedFlow<Boolean>()
        val getIamportTokenResult: SharedFlow<Boolean> = _getIamportTokenResult

        private val _getIamportCertificationResult = MutableSharedFlow<Boolean>()
        val getIamportCertificationResult: SharedFlow<Boolean> = _getIamportCertificationResult

        fun checkAllTerm() {
            isTermPrivateSelected.value = isTermAllSelected.value?.not()
            isTermServiceSelected.value = isTermAllSelected.value?.not()
            isTermMarketingSelected.value = isTermAllSelected.value?.not()
            isTermAllSelected.value = isTermAllSelected.value?.not()
        }

        fun checkPrivateTerm() {
            isTermPrivateSelected.value = isTermPrivateSelected.value?.not()
            checkIsCompleted()
        }

        fun checkServiceTerm() {
            isTermServiceSelected.value = isTermServiceSelected.value?.not()
            checkIsCompleted()
        }

        fun checkMarketingTerm() {
            isTermMarketingSelected.value = isTermMarketingSelected.value?.not()
            checkIsCompleted()
        }

        private fun checkIsCompleted() {
            isCompleted.value =
                (isTermPrivateSelected.value == true && isTermServiceSelected.value == true)
        }

        fun clickSubmitBtn() {
            viewModelScope.launch {
                _isSubmitClicked.emit(true)
            }
        }

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
                            if (name != null && phone != null) {
                                userRepository.setUserInfo(name, phone.toPhoneFrom().orEmpty())
                            }
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
