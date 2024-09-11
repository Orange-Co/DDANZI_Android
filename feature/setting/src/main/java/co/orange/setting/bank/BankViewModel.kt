package co.orange.setting.bank

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.orange.core.state.UiState
import co.orange.domain.entity.response.BankModel
import co.orange.domain.repository.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BankViewModel
    @Inject
    constructor(
        private val settingRepository: SettingRepository,
    ) : ViewModel() {
        var accountId: Long = -1
        var ownerName: String = ""

        private val _getUserBankState = MutableStateFlow<UiState<BankModel>>(UiState.Empty)
        val getUserBankState: StateFlow<UiState<BankModel>> = _getUserBankState

        fun getUserBankFromServer() {
            viewModelScope.launch {
                settingRepository.getUserBank()
                    .onSuccess { response ->
                        response.accountId?.let { accountId = it }
                        response.name?.let { ownerName = it }
                        _getUserBankState.value = UiState.Success(response)
                    }
                    .onFailure {
                        _getUserBankState.value = UiState.Failure(it.message.orEmpty())
                    }
            }
        }
    }
