package co.orange.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.orange.core.state.UiState
import co.orange.domain.entity.response.SettingInfoModel
import co.orange.domain.repository.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel
    @Inject
    constructor(
        private val settingRepository: SettingRepository,
    ) : ViewModel() {
        private val _getSettingInfoState = MutableStateFlow<UiState<SettingInfoModel>>(UiState.Empty)
        val getSettingInfoState: StateFlow<UiState<SettingInfoModel>> = _getSettingInfoState

        init {
            getSettingInfoFromServer()
        }

        private fun getSettingInfoFromServer() {
            _getSettingInfoState.value = UiState.Loading
            viewModelScope.launch {
                settingRepository.getSettingInfo()
                    .onSuccess {
                        _getSettingInfoState.value = UiState.Success(it)
                    }
                    .onFailure {
                        _getSettingInfoState.value = UiState.Failure(it.message.toString())
                    }
            }
        }
    }
