package co.orange.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.orange.core.state.UiState
import co.orange.domain.entity.response.ProductDetailModel
import co.orange.domain.entity.response.ProductOptionModel
import co.orange.domain.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
    @Inject
    constructor(
        private val detailRepository: DetailRepository,
    ) : ViewModel() {
        var productId: String = ""
        var imageUrl: String = ""
        var originPrice: Int = 0
        var salePrice: Int = 0

        var infoUrl: String = ""
        var interestCount: Int = 0
        var optionList = listOf<ProductOptionModel>()

        private val _getProductDetailState =
            MutableStateFlow<UiState<ProductDetailModel>>(UiState.Empty)
        val getProductDetailState: StateFlow<UiState<ProductDetailModel>> = _getProductDetailState

        init {
            getProductDetailFromServer()
        }

        private fun getProductDetailFromServer() {
            viewModelScope.launch {
                detailRepository.getProductDetail(productId)
                    .onSuccess {
                        infoUrl = it.infoUrl
                        interestCount = it.interestCount
                        optionList = it.optionList
                        _getProductDetailState.value = UiState.Success(it)
                    }
                    .onFailure {
                        _getProductDetailState.value = UiState.Failure(it.message.toString())
                    }
            }
        }
    }
