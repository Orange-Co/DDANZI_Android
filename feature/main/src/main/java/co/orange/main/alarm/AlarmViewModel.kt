package co.orange.main.alarm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.orange.core.state.UiState
import co.orange.domain.entity.response.AlarmListModel
import co.orange.domain.entity.response.AlarmListModel.AlarmItemModel
import co.orange.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel
    @Inject
    constructor(
        private val homeRepository: HomeRepository,
    ) : ViewModel() {
        private val _getAlarmListState = MutableStateFlow<UiState<AlarmListModel>>(UiState.Empty)
        val getAlarmListState: StateFlow<UiState<AlarmListModel>> = _getAlarmListState

        private val _patchReadState = MutableStateFlow<UiState<AlarmItemModel>>(UiState.Empty)
        val patchReadState: StateFlow<UiState<AlarmItemModel>> = _patchReadState

        init {
            getAlarmListFromServer()
        }

        private fun getAlarmListFromServer() {
            _getAlarmListState.value = UiState.Loading
            viewModelScope.launch {
                homeRepository.getAlarmList()
                    .onSuccess {
                        _getAlarmListState.value = UiState.Success(it)
                    }
                    .onFailure {
                        _getAlarmListState.value = UiState.Failure(it.message.toString())
                    }
            }
        }

        fun patchToReadAlarmToServer(item: AlarmItemModel) {
            if (item.isChecked) {
                _patchReadState.value = UiState.Success(item)
                return
            }
            _patchReadState.value = UiState.Loading
            viewModelScope.launch {
                homeRepository.patchToReadAlarm(
                    item.alarmId,
                ).onSuccess {
                    _patchReadState.value = UiState.Success(item)
                }.onFailure {
                    _patchReadState.value = UiState.Failure(it.message.toString())
                }
            }
        }
    }
