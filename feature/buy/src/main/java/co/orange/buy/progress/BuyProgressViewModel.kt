package co.orange.buy.progress

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.orange.buy.BuildConfig.PAYMENT_UID
import co.orange.core.amplitude.AmplitudeManager
import co.orange.core.state.UiState
import co.orange.domain.entity.request.OrderRequestModel
import co.orange.domain.entity.request.PayEndRequestModel
import co.orange.domain.entity.request.PayStartRequestModel
import co.orange.domain.entity.response.BuyProgressModel
import co.orange.domain.entity.response.PayEndModel
import co.orange.domain.entity.response.PayStartModel
import co.orange.domain.enums.PaymentMethod
import co.orange.domain.repository.BuyRepository
import com.iamport.sdk.data.sdk.IamPortRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BuyProgressViewModel
    @Inject
    constructor(
        private val buyRepository: BuyRepository,
    ) : ViewModel() {
        var productId: String = ""
        private var orderId: String = ""

        private var buyProgressData: BuyProgressModel? = null
        var optionList = listOf<Long>()

        var payMethodId = MutableLiveData<Int>(-1)
        var payMethod = ""

        var isTermAllSelected = MutableLiveData<Boolean>(false)
        var isTermServiceSelected = MutableLiveData<Boolean>(false)
        var isTermPurchaseSelected = MutableLiveData<Boolean>(false)
        var isCompleted = MutableLiveData<Boolean>(false)
        var isOrderStarted = false

        private val _getBuyDataState = MutableStateFlow<UiState<BuyProgressModel>>(UiState.Empty)
        val getBuyDataState: StateFlow<UiState<BuyProgressModel>> = _getBuyDataState

        private val _postPayStartState = MutableStateFlow<UiState<PayStartModel>>(UiState.Empty)
        val postPayStartState: StateFlow<UiState<PayStartModel>> = _postPayStartState

        private val _patchPayEndState = MutableStateFlow<UiState<PayEndModel>>(UiState.Empty)
        val patchPayEndState: StateFlow<UiState<PayEndModel>> = _patchPayEndState

        private val _postOrderState = MutableStateFlow<UiState<String>>(UiState.Empty)
        val postOrderState: StateFlow<UiState<String>> = _postOrderState

        fun checkAllTerm() {
            AmplitudeManager.trackEvent("click_purchase_terms_all")
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

        fun setPayMethod(methodId: Int) {
            payMethodId.value = methodId
            payMethod = PaymentMethod.fromId(methodId)?.methodName ?: PAYMENT_DEFAULT
            checkIsCompleted()
        }

        private fun checkIsCompleted() {
            isTermAllSelected.value =
                (isTermServiceSelected.value == true && isTermPurchaseSelected.value == true)
            isCompleted.value =
                (isTermAllSelected.value == true && !buyProgressData?.addressInfo?.address.isNullOrBlank() && payMethod.isNotBlank())
        }

        fun getBuyDataFromServer() {
            _getBuyDataState.value = UiState.Loading
            viewModelScope.launch {
                buyRepository.getBuyProgressData(productId)
                    .onSuccess {
                        checkIsCompleted()
                        buyProgressData = it
                        _getBuyDataState.value = UiState.Success(it)
                    }
                    .onFailure {
                        _getBuyDataState.value = UiState.Failure(it.message.toString())
                    }
            }
        }

        fun postPayStartToServer() {
            _postPayStartState.value = UiState.Loading
            isOrderStarted = true
            viewModelScope.launch {
                buyRepository.postPaymentStart(
                    PayStartRequestModel(
                        buyProgressData?.productId.orEmpty(),
                        buyProgressData?.charge ?: -1,
                        buyProgressData?.totalPrice ?: -1,
                        payMethod,
                    ),
                ).onSuccess {
                    orderId = it.orderId
                    if (it.payStatus == PAY_STATUS_PENDING) {
                        _postPayStartState.value = UiState.Success(it)
                    } else {
                        _postPayStartState.value = UiState.Failure(it.payStatus)
                    }
                }.onFailure {
                    _postPayStartState.value = UiState.Failure(it.message.orEmpty())
                }
            }
        }

        fun createIamportRequest(): IamPortRequest? {
            return if (buyProgressData?.productName.isNullOrBlank() || payMethod.isBlank() || orderId.isBlank()) {
                Timber.tag("okhttp").d("IAMPORT PURCHASE REQUEST ERROR : $buyProgressData")
                null
            } else {
                IamPortRequest(
                    pg = NICE_PAYMENTS,
                    pay_method = payMethod,
                    name = buyProgressData?.modifiedProductName,
                    merchant_uid = orderId,
                    amount = buyProgressData?.totalPrice.toString(),
                    buyer_name = buyProgressData?.addressInfo?.recipient,
                    buyer_tel = buyProgressData?.addressInfo?.recipientPhone,
                    digital = false,
                )
            }
        }

        fun patchPayEndToServer(isSuccess: Boolean?) {
            _patchPayEndState.value = UiState.Loading
            viewModelScope.launch {
                buyRepository.patchPaymentEnd(
                    PayEndRequestModel(
                        orderId,
                        if (isSuccess != false) PAY_SUCCESS else PAY_FAILURE,
                    ),
                ).onSuccess {
                    _patchPayEndState.value = UiState.Success(it)
                }.onFailure {
                    _patchPayEndState.value = UiState.Failure(it.message.orEmpty())
                }
            }
        }

        fun postToRequestOrderToServer() {
            _postOrderState.value = UiState.Loading
            viewModelScope.launch {
                buyRepository.postToRequestOrder(
                    OrderRequestModel(
                        orderId,
                        optionList,
                    ),
                ).onSuccess {
                    _postOrderState.value = UiState.Success(it.orderId)
                }.onFailure {
                    _postOrderState.value = UiState.Failure(it.message.orEmpty())
                }
            }
        }

        companion object {
            private const val NICE_PAYMENTS = "nice_v2.$PAYMENT_UID"

            private const val PAYMENT_DEFAULT = "card"

            private const val PAY_STATUS_PENDING = "PENDING"
            const val PAY_SUCCESS = "PAID"
            const val PAY_FAILURE = "FAILED"
        }
    }
