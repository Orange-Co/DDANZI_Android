package co.orange.presentation.bank

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.orange.core.extension.maskName
import co.orange.domain.entity.request.BankRequestModel
import co.orange.domain.repository.SettingRepository
import co.orange.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddBankViewModel
    @Inject
    constructor(
        private val settingRepository: SettingRepository,
        private val userRepository: UserRepository,
    ) : ViewModel() {
        var ownerName = MutableLiveData<String>()
        var bankName = MutableLiveData<String>()
        var accountNumber = MutableLiveData<String>()

        val isBankSelected = MutableLiveData(false)
        val isCompleted = MutableLiveData(false)

        private val _setBankResult = MutableSharedFlow<Boolean>()
        val setBankResult: SharedFlow<Boolean> = _setBankResult

        init {
            getUserName()
        }

        private fun getUserName() {
            ownerName.value =
                userRepository.getUserName().takeIf { it.isNotEmpty() }?.maskName() ?: return
        }

        fun checkIsCompleted() {
            isCompleted.value =
                (ownerName.value != null && isBankSelected.value == true && accountNumber.value != null)
        }

        fun postToAddBankToServer() {
            viewModelScope.launch {
                settingRepository.postToAddBank(
                    BankRequestModel(
                        ownerName.value.orEmpty(),
                        bankName.value.orEmpty(),
                        accountNumber.value.orEmpty(),
                    ),
                ).onSuccess {
                    _setBankResult.emit(true)
                }.onFailure {
                    _setBankResult.emit(false)
                }
            }
        }

        fun putToModBankToServer(accountId: Long) {
            viewModelScope.launch {
                settingRepository.putToModBank(
                    accountId,
                    BankRequestModel(
                        ownerName.value.orEmpty(),
                        bankName.value.orEmpty(),
                        accountNumber.value.orEmpty(),
                    ),
                ).onSuccess {
                    _setBankResult.emit(true)
                }.onFailure {
                    _setBankResult.emit(false)
                }
            }
        }
    }
