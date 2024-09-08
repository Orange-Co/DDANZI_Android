package co.orange.setting.delivery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.orange.core.state.UiState
import co.orange.domain.entity.response.AddressModel
import co.orange.domain.repository.SettingRepository
import co.orange.setting.delivery.address.AddressActivity.Companion.DEFAULT_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeliveryViewModel
    @Inject
    constructor(
        private val settingRepository: SettingRepository,
    ) : ViewModel() {
        var addressId: Long = DEFAULT_ID

        private val _getUserAddressState = MutableStateFlow<UiState<AddressModel>>(UiState.Empty)
        val getUserAddressState: StateFlow<UiState<AddressModel>> = _getUserAddressState

        private val _deleteAddressResult = MutableSharedFlow<Boolean>()
        val deleteAddressResult: SharedFlow<Boolean> = _deleteAddressResult

        fun getUserAddressFromServer() {
            viewModelScope.launch {
                settingRepository.getUserAddress()
                    .onSuccess {
                        it.addressId?.let { addressId = it }
                        _getUserAddressState.value = UiState.Success(it)
                    }
                    .onFailure {
                        _getUserAddressState.value = UiState.Failure(it.message.orEmpty())
                    }
            }
        }

        fun deleteUserAddressFromServer() {
            viewModelScope.launch {
                settingRepository.deleteUserAddress(addressId)
                    .onSuccess {
                        _deleteAddressResult.emit(true)
                    }
                    .onFailure {
                        _deleteAddressResult.emit(false)
                    }
            }
        }
    }
