package co.orange.presentation.address

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
        var detailAddress = ""
        var name = ""
        var phone = ""

        fun getUserName(): String {
            name = userRepository.getUserName()
            return name
        }

        fun getUserPhone(): String {
            phone = userRepository.getUserPhone()
            return phone
        }
    }
