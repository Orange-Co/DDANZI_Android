package co.orange.presentation.auth.phone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.orange.core.state.UiState
import co.orange.domain.entity.response.IamportTokenModel
import co.orange.domain.repository.IamportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhoneViewModel
    @Inject
    constructor(
        private val iamportRepository: IamportRepository,
    ) : ViewModel() {
        var certificatedUid: String = ""

        private val _getIamportTokenResult = MutableSharedFlow<Boolean>()
        val getIamportTokenResult: SharedFlow<Boolean> = _getIamportTokenResult

        private val _getIamportDataState =
            MutableStateFlow<UiState<IamportTokenModel>>(UiState.Empty)
        val getIamportDataState: StateFlow<UiState<IamportTokenModel>> = _getIamportDataState

        fun postToGetIamportTokenFromServer() {
            viewModelScope.launch {
                iamportRepository.postToGetIamportToken()
                    .onSuccess {
                        _getIamportTokenResult.emit(true)
                    }
                    .onFailure {
                        _getIamportTokenResult.emit(false)
                    }
            }
        }
    }
