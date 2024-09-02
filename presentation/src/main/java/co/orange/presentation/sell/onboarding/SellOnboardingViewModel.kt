package co.orange.presentation.sell.onboarding

import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.orange.core.extension.getFileName
import co.orange.core.state.UiState
import co.orange.domain.entity.request.SellCheckRequestModel
import co.orange.domain.repository.SellRepository
import co.orange.domain.repository.UploadRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SellOnboardingViewModel
    @Inject
    constructor(
        private val sellRepository: SellRepository,
        private val uploadRepository: UploadRepository,
    ) : ViewModel() {
        private var selectedImageUri = ""
        private var selectedImageName = ""
        var uploadedUrl = ""

        var productId = ""
        var productName = ""

        private val _isCheckedAgain = MutableSharedFlow<Boolean>()
        val isCheckedAgain: SharedFlow<Boolean> = _isCheckedAgain

        private val _changingImageState = MutableStateFlow<UiState<String>>(UiState.Empty)
        val changingImageState: StateFlow<UiState<String>> = _changingImageState

        fun setCheckedAgain(state: Boolean) {
            viewModelScope.launch {
                _isCheckedAgain.emit(state)
            }
        }

        fun startSendingImage(
            uri: Uri,
            contentResolver: ContentResolver,
        ) {
            selectedImageUri = uri.toString()
            selectedImageName = uri.getFileName(contentResolver).orEmpty()
            _changingImageState.value = UiState.Loading
            viewModelScope.launch {
                sellRepository.getSignedUrl(selectedImageName)
                    .onSuccess {
                        uploadedUrl = it.signedUrl
                        putImageToCloud(it.signedUrl)
                    }
                    .onFailure {
                        _changingImageState.value = UiState.Failure(it.message.toString())
                        _changingImageState.value = UiState.Empty
                    }
            }
        }

        private fun putImageToCloud(url: String) {
            viewModelScope.launch {
                uploadRepository.putImageToCloud(url, selectedImageUri)
                    .onSuccess {
                        postToCheckProduct()
                    }.onFailure {
                        _changingImageState.value = UiState.Failure(it.message.toString())
                        _changingImageState.value = UiState.Empty
                    }
            }
        }

        private fun postToCheckProduct() {
            viewModelScope.launch {
                sellRepository.postToCheckProduct(SellCheckRequestModel(uploadedUrl))
                    .onSuccess {
                        productId = it.productId
                        productName = it.productName
                        _changingImageState.value = UiState.Success(it.productId)
                    }
                    .onFailure {
                        _changingImageState.value = UiState.Failure(it.message.toString())
                        _changingImageState.value = UiState.Empty
                    }
            }
        }

        fun resetProductIdState() {
            _changingImageState.value = UiState.Empty
        }
    }
