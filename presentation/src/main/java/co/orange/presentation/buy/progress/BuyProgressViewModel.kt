package co.orange.presentation.buy.progress

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.orange.core.state.UiState
import co.orange.domain.entity.response.BuyProgressModel
import co.orange.domain.repository.BuyRepository
import com.iamport.sdk.data.sdk.IamPortRequest
import com.iamport.sdk.data.sdk.PayMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kr.genti.presentation.BuildConfig.PAYMENT_UID
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BuyProgressViewModel
    @Inject
    constructor(
        private val buyRepository: BuyRepository,
    ) : ViewModel() {
        var productId: String = ""
        var optionList: List<Long>? = null
        var buyProgressData: BuyProgressModel? = null
        var payMethod: String = ""

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
                        buyProgressData = it
                        _getBuyProgressDataState.value = UiState.Success(it)
                    }
                    .onFailure {
                        _getBuyProgressDataState.value = UiState.Failure(it.message.toString())
                    }
            }
        }

        fun createIamportRequest(): IamPortRequest? {
            // TODO  || payMethod.isBlank() 추가
            return if (buyProgressData?.productName.isNullOrBlank()) {
                Timber.tag("okhttp").d("IAMPORT PURCHASE REQUEST ERROR : $buyProgressData & $payMethod")
                null
            } else {
                IamPortRequest(
                    pg = NICE_PAYMENTS,
                    // TODO 결제방법 수정
                    pay_method = PayMethod.card.name,
                    // TODO 추후 수정
                    name = "예시상품",
                    merchant_uid = "0123456789",
                    amount = buyProgressData?.totalPrice.toString(),
                    buyer_name = buyProgressData?.addressInfo?.recipient,
                    buyer_tel = buyProgressData?.addressInfo?.recipientPhone,
                )
            }
        }

        companion object {
            private const val NICE_PAYMENTS = "nice_v2.{$PAYMENT_UID}"
        }
    }
