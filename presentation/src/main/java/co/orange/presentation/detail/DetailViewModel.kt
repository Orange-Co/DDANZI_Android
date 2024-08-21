package co.orange.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.orange.core.state.UiState
import co.orange.domain.entity.response.ProductDetailModel
import co.orange.domain.entity.response.ProductOptionModel
import co.orange.domain.repository.DeviceRepository
import co.orange.domain.repository.InterestRepository
import co.orange.domain.repository.UserRepository
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
        private val userRepository: UserRepository,
    ) : ViewModel() {
        var productId: String = ""

        var infoUrl: String = ""
        var interestCount: Int = 0
        var optionList = listOf<ProductOptionModel>()
        var selectedOptionList = mutableListOf<Long>()

        private val _getProductDetailState =
            MutableStateFlow<UiState<ProductDetailModel>>(UiState.Empty)
        val getProductDetailState: StateFlow<UiState<ProductDetailModel>> = _getProductDetailState

        private val _likeState = MutableStateFlow<Boolean>(false)
        val likeState: StateFlow<Boolean> = _likeState

        var isLikeLottieNeeded = false

        fun getProductDetailFromServer() {
            viewModelScope.launch {
                deviceRepository.getProductDetail(productId)
                    .onSuccess {
                        infoUrl = it.infoUrl
                        interestCount = it.interestCount
                        optionList = it.optionList
                        selectedOptionList = MutableList(optionList.size) { -1 }
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
                            isLikeLottieNeeded = true
                            interestCount += 1
                            _likeState.value = true
                        }
                }
            }
        }

        fun getUserLogined() = userRepository.getAccessToken().isNotEmpty()
    }
