package co.orange.sell.confirm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.orange.core.state.UiState
import co.orange.domain.entity.response.SellBuyerInfoModel
import co.orange.domain.enums.OrderStatus
import co.orange.domain.repository.SellRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SellConfirmViewModel
    @Inject
    constructor(
        private val sellRepository: SellRepository,
    ) : ViewModel() {
        var orderId = ""

        private val _getBuyerInfoState = MutableStateFlow<UiState<SellBuyerInfoModel>>(UiState.Empty)
        val getBuyerInfoState: StateFlow<UiState<SellBuyerInfoModel>> = _getBuyerInfoState

        private val _patchOrderConfirmResult = MutableSharedFlow<Boolean>()
        val patchOrderConfirmResult: SharedFlow<Boolean> = _patchOrderConfirmResult

        fun getBuyerInfoFromServer() {
            _getBuyerInfoState.value = UiState.Loading
            viewModelScope.launch {
                sellRepository.getBuyerInfo(orderId)
                    .onSuccess {
                        _getBuyerInfoState.value = UiState.Success(it)
                    }.onFailure {
                        _getBuyerInfoState.value = UiState.Failure(it.message.orEmpty())
                    }
            }
        }

        fun patchOrderConfirmToServer() {
            viewModelScope.launch {
                sellRepository.patchOrderConfirm(orderId)
                    .onSuccess {
                        if (it.orderStatus == OrderStatus.COMPLETED.name) {
                            _patchOrderConfirmResult.emit(true)
                        } else {
                            _patchOrderConfirmResult.emit(false)
                        }
                    }
                    .onFailure {
                        _patchOrderConfirmResult.emit(false)
                    }
            }
        }
    }
