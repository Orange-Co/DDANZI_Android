package co.orange.presentation.auth.signup_x

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel
    @Inject
    constructor(
        // private val authRepository: AuthRepository,
    ) : ViewModel() {
        val name = MutableLiveData<String>()
        val isNameFinished = MutableLiveData(false)

        val birthFront = MutableLiveData<String>()
        val isBirthFrontFinished = MutableLiveData(false)

        val birthBack = MutableLiveData<String>()
        val isBirthBackFinished = MutableLiveData(false)

        val mobile = MutableLiveData<String>("선택해주세요")
        val isMobileFinished = MutableLiveData(false)

        val phone = MutableLiveData<String>()
        val isPhoneFinished = MutableLiveData(false)

        val code = MutableLiveData<String>()
        val isCodeFinished = MutableLiveData(false)

        val isCompleted = MutableLiveData(false)

        fun checkNameFinished() {
            isNameFinished.value = name.value?.isNotEmpty()
            checkIsCompleted()
        }

        fun checkBirthFrontFinished() {
            isBirthFrontFinished.value = birthFront.value?.length == 6
            checkIsCompleted()
        }

        fun checkBirthBackFinished() {
            isBirthBackFinished.value = birthBack.value?.length == 1
            checkIsCompleted()
        }

        fun checkPhoneFinished() {
            isPhoneFinished.value = phone.value?.length == 11
        }

        fun checkCodeFinished() {
            isCodeFinished.value = code.value?.length == 6
            checkIsCompleted()
        }

        fun checkIsCompleted() {
            isCompleted.value =
                (isNameFinished.value == true && isBirthFrontFinished.value == true && isBirthBackFinished.value == true && isMobileFinished.value == true && isPhoneFinished.value == true && isCodeFinished.value == true)
        }
    }
