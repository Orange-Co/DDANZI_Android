package co.orange.presentation.buy.progress

import androidx.lifecycle.MutableLiveData
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

        var isTermAllSelected = MutableLiveData<Boolean>(false)
        var isTermServiceSelected = MutableLiveData<Boolean>(false)
        var isTermPurchaseSelected = MutableLiveData<Boolean>(false)
        var isAddressSelected = false
        var isMethodSelected = true
        var isCompleted = MutableLiveData<Boolean>(false)

        private val _getBuyProgressDataState =
            MutableStateFlow<UiState<BuyProgressModel>>(UiState.Empty)
        val getBuyProgressDataState: StateFlow<UiState<BuyProgressModel>> = _getBuyProgressDataState

        fun checkAllTerm() {
            isTermServiceSelected.value = isTermAllSelected.value?.not()
            isTermPurchaseSelected.value = isTermAllSelected.value?.not()
            isTermAllSelected.value = isTermAllSelected.value?.not()
            checkIsCompleted()
        }

        fun checkServiceTerm() {
            isTermServiceSelected.value = isTermServiceSelected.value?.not()
            checkIsCompleted()
        }

        fun checkPurchaseTerm() {
            isTermPurchaseSelected.value = isTermPurchaseSelected.value?.not()
            checkIsCompleted()
        }

        private fun checkIsCompleted() {
            isTermAllSelected.value =
                (isTermServiceSelected.value == true && isTermPurchaseSelected.value == true)
            isCompleted.value =
                (isTermServiceSelected.value == true && isTermPurchaseSelected.value == true && isAddressSelected && isMethodSelected)
        }

        fun getBuyProgressDataFromServer() {
            _getBuyProgressDataState.value = UiState.Loading
            viewModelScope.launch {
                // TODO 추후 productId 활용
                buyRepository.getBuyProgressData("0110055338")
                    .onSuccess {
                        isAddressSelected = !it.addressInfo.address.isNullOrEmpty()
                        checkIsCompleted()
                        _getBuyProgressDataState.value = UiState.Success(it)
                    }
                    .onFailure {
                        _getBuyProgressDataState.value = UiState.Failure(it.message.toString())
                    }
            }
        }
    }
