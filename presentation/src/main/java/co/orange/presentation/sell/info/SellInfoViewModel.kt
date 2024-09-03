package co.orange.presentation.sell.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.orange.core.state.UiState
import co.orange.domain.entity.response.SellInfoModel
import co.orange.domain.repository.SellRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SellInfoViewModel
    @Inject
    constructor(
        private val sellRepository: SellRepository,
    ) : ViewModel() {
        var itemId = ""
        var orderId = ""

        private val _getSellInfoState = MutableStateFlow<UiState<SellInfoModel>>(UiState.Empty)
        val getSellInfoState: StateFlow<UiState<SellInfoModel>> = _getSellInfoState

        fun getItemDetailInfoFromServer() {
            _getSellInfoState.value = UiState.Loading
            viewModelScope.launch {
                sellRepository.getItemDetailInfo(itemId)
                    .onSuccess {
                        orderId = it.orderId
                        _getSellInfoState.value = UiState.Success(it)
                    }.onFailure {
                        _getSellInfoState.value = UiState.Failure(it.message.orEmpty())
                    }
            }
        }
    }
