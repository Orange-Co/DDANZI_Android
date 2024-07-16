package co.orange.presentation.main.home

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.orange.core.state.UiState
import co.orange.domain.entity.response.HomeModel
import co.orange.domain.repository.HomeRepository
import co.orange.domain.repository.UserRepository
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val homeRepository: HomeRepository,
        private val userRepository: UserRepository,
    ) : ViewModel() {
        init {
            getHomeDataFromServer()
        }

        var selectedImageUri = Uri.EMPTY
        var productId = ""

        private val _isCheckedAgain = MutableSharedFlow<Boolean>()
        val isCheckedAgain: SharedFlow<Boolean> = _isCheckedAgain

        private val _getHomeDataState = MutableStateFlow<UiState<HomeModel>>(UiState.Empty)
        val getHomeDataState: StateFlow<UiState<HomeModel>> = _getHomeDataState

        private val _getProductIdState = MutableStateFlow<UiState<String>>(UiState.Empty)
        val getProductIdState: StateFlow<UiState<String>> = _getProductIdState

        fun setCheckedState(state: Boolean) {
            viewModelScope.launch {
                _isCheckedAgain.emit(state)
            }
        }

        private fun getHomeDataFromServer() {
            viewModelScope.launch {
                homeRepository.getHomeData()
                    .onSuccess {
                        _getHomeDataState.value = UiState.Success(it)
                    }
                    .onFailure {
                        _getHomeDataState.value = UiState.Failure(it.message.toString())
                    }
            }
        }

        fun showCaptureImage(
            img: Uri,
            context: Context,
        ) {
            runCatching {
                val image = InputImage.fromFilePath(context, img)
                val recognizer =
                    TextRecognition.getClient(KoreanTextRecognizerOptions.Builder().build())
                recognizer.process(image)
                    .addOnSuccessListener {
                        // TODO 서버통신으로 ID값 가져오기
                        _getProductIdState.value = UiState.Success(it.text)
                    }
                    .addOnFailureListener {
                        _getProductIdState.value = UiState.Failure(it.message.toString())
                    }
            }
        }

        fun resetProductIdState() {
            _getProductIdState.value = UiState.Empty
        }

        fun setDeviceToken(deviceToken: String) {
            userRepository.setDeviceToken(deviceToken)
        }
    }
