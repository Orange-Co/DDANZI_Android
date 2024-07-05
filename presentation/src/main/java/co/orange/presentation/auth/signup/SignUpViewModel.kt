package co.orange.presentation.auth.signup

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

        val mobile = MutableLiveData<String>()
        val isMobileFinished = MutableLiveData(false)

        val phone = MutableLiveData<String>()
        val isPhoneFinished = MutableLiveData(false)

        val code = MutableLiveData<String>()
        val isCodeFinished = MutableLiveData(false)

        val isCompleted = MutableLiveData(false)

        fun checkNameFinished() {
            isNameFinished.value = name.value?.isNotEmpty()
        }

        fun checkBirthFrontFinished() {
            isBirthFrontFinished.value = birthFront.value?.length == 6
        }

        fun checkBirthBackFinished() {
            isBirthBackFinished.value = birthBack.value?.length == 1
        }

        fun checkPhoneFinished() {
            isPhoneFinished.value = phone.value?.length == 11
        }

        fun checkCodeFinished() {
            isCodeFinished.value = code.value?.length == 6
        }

        fun checkIsCompleted() {
            isCompleted.value =
                (isNameFinished.value == true && isBirthFrontFinished.value == true && isBirthBackFinished.value == true && isMobileFinished.value == true && isPhoneFinished.value == true && isCodeFinished.value == true)
        }
    }
