package co.orange.presentation.auth.phone

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.orange.core.extension.toPhoneFrom
import co.orange.core.state.UiState
import co.orange.domain.entity.request.SignUpRequestModel
import co.orange.domain.repository.AuthRepository
import co.orange.domain.repository.IamportRepository
import co.orange.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PhoneViewModel
    @Inject
    constructor(
        private val authRepository: AuthRepository,
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

        private val _postSignUpState = MutableStateFlow<UiState<String>>(UiState.Empty)
        val postSignUpState: StateFlow<UiState<String>> = _postSignUpState

        fun checkAllTerm() {
            isTermPrivateSelected.value = isTermAllSelected.value?.not()
            isTermServiceSelected.value = isTermAllSelected.value?.not()
            isTermMarketingSelected.value = isTermAllSelected.value?.not()
            isTermAllSelected.value = isTermAllSelected.value?.not()
            checkIsCompleted()
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
            isTermAllSelected.value =
                (isTermPrivateSelected.value == true && isTermServiceSelected.value == true && isTermMarketingSelected.value == true)
        }

        fun clickSubmitBtn(boolean: Boolean) {
            viewModelScope.launch {
                _isSubmitClicked.emit(boolean)
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
                        Timber.tag("okhttp").d("IAMPORT TOKEN ERROR : $it")
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
                            postToSignUpFromServer(
                                SignUpRequestModel(
                                    it.name.orEmpty(),
                                    it.phone.orEmpty(),
                                    it.birthday?.replace("-", ".").orEmpty(),
                                    it.gender?.uppercase().orEmpty(),
                                ),
                            )
                            userRepository.setUserInfo(
                                it.name.orEmpty(),
                                it.phone?.toPhoneFrom().orEmpty(),
                            )
                        } else {
                            _getIamportCertificationResult.emit(false)
                        }
                    }
                    .onFailure {
                        Timber.tag("okhttp").d("IAMPORT DATA ERROR : $it")
                        _getIamportCertificationResult.emit(false)
                    }
            }
        }

        private fun postToSignUpFromServer(request: SignUpRequestModel) {
            viewModelScope.launch {
                authRepository.postToSignUp(userRepository.getAccessToken(), request)
                    .onSuccess {
                        _postSignUpState.value = UiState.Success(it.nickname)
                    }
                    .onFailure {
                        Timber.tag("okhttp").d(it)
                        _postSignUpState.value = UiState.Failure(it.message.orEmpty())
                    }
            }
        }
    }
