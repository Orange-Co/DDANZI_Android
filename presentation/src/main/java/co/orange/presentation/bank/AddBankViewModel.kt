package co.orange.presentation.bank

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.orange.core.extension.maskName
import co.orange.domain.repository.SettingRepository
import co.orange.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddBankViewModel
    @Inject
    constructor(
        private val settingRepository: SettingRepository,
        private val userRepository: UserRepository,
    ) : ViewModel() {
        var accountId: Long = -1
        var ownerName = MutableLiveData<String>()
        var bankName = MutableLiveData<String>()
        var accountNumber = MutableLiveData<String>()

        val isBankSelected = MutableLiveData(false)
        val isCompleted = MutableLiveData(false)

        init {
            getUserName()
        }

        private fun getUserName() {
            ownerName.value = userRepository.getUserName().takeIf { it.isNotEmpty() }?.maskName() ?: return
        }

        fun checkIsCompleted() {
            isCompleted.value =
                (ownerName.value != null && isBankSelected.value == true && accountNumber.value != null)
        }
    }
