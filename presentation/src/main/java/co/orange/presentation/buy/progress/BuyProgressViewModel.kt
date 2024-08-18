package co.orange.presentation.buy.progress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.orange.core.state.UiState
import co.orange.domain.entity.response.BuyProgressModel
import co.orange.domain.repository.BuyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuyProgressViewModel
    @Inject
    constructor(
        private val buyRepository: BuyRepository,
    ) : ViewModel() {
        var productId: String = ""

        private val _getBuyProgressDataState =
            MutableStateFlow<UiState<BuyProgressModel>>(UiState.Empty)
        val getBuyProgressDataState: StateFlow<UiState<BuyProgressModel>> = _getBuyProgressDataState

        fun getBuyProgressDataFromServer() {
            _getBuyProgressDataState.value = UiState.Loading
            viewModelScope.launch {
                // TODO 추후 productId 활용
                buyRepository.getBuyProgressData("0110055338")
                    .onSuccess {
                        _getBuyProgressDataState.value = UiState.Success(it)
                    }
                    .onFailure {
                        _getBuyProgressDataState.value = UiState.Failure(it.message.toString())
                    }
            }
        }
    }
