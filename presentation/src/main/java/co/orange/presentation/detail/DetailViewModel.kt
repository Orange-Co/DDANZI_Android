package co.orange.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.orange.core.state.UiState
import co.orange.domain.entity.response.ProductDetailModel
import co.orange.domain.entity.response.ProductOptionModel
import co.orange.domain.repository.DeviceRepository
import co.orange.domain.repository.InterestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
    @Inject
    constructor(
        private val deviceRepository: DeviceRepository,
        private val interestRepository: InterestRepository,
    ) : ViewModel() {
        var productId: String = ""

        var infoUrl: String = ""
        var interestCount: Int = 0
        var optionList = listOf<ProductOptionModel>()

        private val _getProductDetailState =
            MutableStateFlow<UiState<ProductDetailModel>>(UiState.Empty)
        val getProductDetailState: StateFlow<UiState<ProductDetailModel>> = _getProductDetailState

        private val _likeState =
            MutableStateFlow<Boolean>(false)
        val likeState: StateFlow<Boolean> = _likeState

        fun getProductDetailFromServer() {
            viewModelScope.launch {
                deviceRepository.getProductDetail(productId)
                    .onSuccess {
                        infoUrl = it.infoUrl
                        interestCount = it.interestCount
                        optionList = it.optionList
                        _likeState.value = it.isInterested
                        _getProductDetailState.value = UiState.Success(it)
                    }
                    .onFailure {
                        _getProductDetailState.value = UiState.Failure(it.message.toString())
                    }
            }
        }

        fun setLikeStateWithServer() {
            viewModelScope.launch {
                if (likeState.value) {
                    interestRepository.deleteInterest(productId)
                        .onSuccess {
                            interestCount -= 1
                            _likeState.value = false
                        }
                } else {
                    interestRepository.postInterest(productId)
                        .onSuccess {
                            interestCount += 1
                            _likeState.value = true
                        }
                }
            }
        }
    }
