package co.orange.presentation.setting.delivery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.orange.core.state.UiState
import co.orange.domain.entity.response.AddressModel
import co.orange.domain.repository.SettingRepository
import co.orange.presentation.address.AddressActivity.Companion.DEFAULT_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
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

        init {
            getUserAddressFromServer()
        }

        private fun getUserAddressFromServer() {
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
    }
