package co.orange.setting.delivery.address

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.orange.domain.entity.request.AddressRequestModel
import co.orange.domain.repository.SettingRepository
import co.orange.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel
    @Inject
    constructor(
        private val settingRepository: SettingRepository,
        private val userRepository: UserRepository,
    ) : ViewModel() {
        var zipCode = ""
        var address = ""
        var detailAddress = MutableLiveData<String>()
        var name = MutableLiveData<String>()
        var phone = MutableLiveData<String>()

        val isCompleted = MutableLiveData(false)

        private val _setAddressResult = MutableSharedFlow<Boolean>()
        val setAddressResult: SharedFlow<Boolean> = _setAddressResult

        init {
            getUserName()
            getUserPhone()
            getUserInfoFromServer()
        }

        private fun getUserName() {
            name.value = userRepository.getUserName()
        }

        private fun getUserPhone() {
            phone.value = userRepository.getUserPhone()
        }

        fun checkIsCompleted() {
            isCompleted.value =
                (
                    zipCode.isNotEmpty() &&
                        address.isNotEmpty() &&
                        detailAddress.value?.isNotEmpty() == true &&
                        name.value?.isNotEmpty() == true &&
                        phone.value?.length == 11
                )
        }

        private fun getUserInfoFromServer() {
            viewModelScope.launch {
                settingRepository.getSettingInfo()
                    .onSuccess {
                        name.value = it.name
                        phone.value = it.phone
                        userRepository.setUserInfo(it.name, it.phone)
                    }
            }
        }

        fun postToAddAddressToServer() {
            viewModelScope.launch {
                settingRepository.postToAddAddress(
                    AddressRequestModel(
                        name.value.orEmpty(),
                        zipCode,
                        TYPE_ROAD,
                        address,
                        detailAddress.value.orEmpty(),
                        phone.value.orEmpty(),
                    ),
                ).onSuccess {
                    _setAddressResult.emit(true)
                }.onFailure {
                    _setAddressResult.emit(false)
                }
            }
        }

        fun putToModAddressToServer(addressId: Long) {
            viewModelScope.launch {
                settingRepository.putToModAddress(
                    addressId,
                    AddressRequestModel(
                        name.value.orEmpty(),
                        zipCode,
                        TYPE_ROAD,
                        address,
                        detailAddress.value.orEmpty(),
                        phone.value.orEmpty(),
                    ),
                ).onSuccess {
                    _setAddressResult.emit(true)
                }.onFailure {
                    _setAddressResult.emit(false)
                }
            }
        }

        companion object {
            private const val TYPE_ROAD = "ROAD"
        }
    }
