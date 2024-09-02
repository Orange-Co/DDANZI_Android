package co.orange.presentation.sell.progress

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.orange.core.state.UiState
import co.orange.domain.entity.response.SellProductModel
import co.orange.domain.repository.SellRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SellProgressViewModel
    @Inject
    constructor(
        private val sellRepository: SellRepository,
    ) : ViewModel() {
        var productId = ""
        var isAccountExist = false

        var sellDate = MutableLiveData<String>()
        var isDateSelected = MutableLiveData<Boolean>(false)
        var isTermAllSelected = MutableLiveData<Boolean>(false)
        var isTermServiceSelected = MutableLiveData<Boolean>(false)
        var isTermSellSelected = MutableLiveData<Boolean>(false)
        var isCompleted = MutableLiveData<Boolean>(false)

        private val _getProductState = MutableStateFlow<UiState<SellProductModel>>(UiState.Empty)
        val getProductState: StateFlow<UiState<SellProductModel>> = _getProductState

        fun checkAllTerm() {
            isTermServiceSelected.value = isTermAllSelected.value?.not()
            isTermSellSelected.value = isTermAllSelected.value?.not()
            isTermAllSelected.value = isTermAllSelected.value?.not()
            checkIsCompleted()
        }

        fun checkServiceTerm() {
            isTermServiceSelected.value = isTermServiceSelected.value?.not()
            checkIsCompleted()
        }

        fun checkSellTerm() {
            isTermSellSelected.value = isTermSellSelected.value?.not()
            checkIsCompleted()
        }

        private fun checkIsCompleted() {
            isTermAllSelected.value =
                (isTermServiceSelected.value == true && isTermSellSelected.value == true)
            isCompleted.value = (isTermAllSelected.value == true && isDateSelected.value == true)
        }

        fun getProductWIthId() {
            if (productId.isEmpty()) {
                _getProductState.value = UiState.Failure(productId)
                return
            }
            _getProductState.value = UiState.Loading
            viewModelScope.launch {
                sellRepository.getProductToSell(productId)
                    .onSuccess {
                        isAccountExist = it.isAddressExist
                        _getProductState.value = UiState.Success(it)
                    }
                    .onFailure {
                        _getProductState.value = UiState.Failure(it.message.orEmpty())
                    }
            }
        }
    }
