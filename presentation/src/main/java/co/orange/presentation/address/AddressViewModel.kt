package co.orange.presentation.address

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.orange.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddressViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) : ViewModel() {
        var zipCode = ""
        var address = ""
        var detailAddress = MutableLiveData<String>()
        var name = MutableLiveData<String>()
        var phone = MutableLiveData<String>()

        val isCompleted = MutableLiveData(false)

        init {
            getUserName()
            getUserPhone()
        }

        private fun getUserName() {
            name.value = userRepository.getUserName()
        }

        private fun getUserPhone() {
            phone.value = userRepository.getUserPhone()
        }

        fun checkIsCompleted() {
            isCompleted.value =
                (zipCode.isNotEmpty() && address.isNotEmpty() && detailAddress.value != null && name.value != null && phone.value != null)
        }
    }
