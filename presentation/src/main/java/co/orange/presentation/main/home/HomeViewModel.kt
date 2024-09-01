package co.orange.presentation.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.orange.core.state.UiState
import co.orange.domain.entity.response.HomeModel
import co.orange.domain.repository.HomeRepository
import co.orange.domain.repository.InterestRepository
import co.orange.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val homeRepository: HomeRepository,
        private val userRepository: UserRepository,
        private val interestRepository: InterestRepository,
    ) : ViewModel() {
        private val _getHomeDataState = MutableStateFlow<UiState<HomeModel>>(UiState.Empty)
        val getHomeDataState: StateFlow<UiState<HomeModel>> = _getHomeDataState

        private val _itemLikePlusState = MutableStateFlow<UiState<Int>>(UiState.Empty)
        val itemLikePlusState: StateFlow<UiState<Int>> = _itemLikePlusState

        private val _itemLikeMinusState = MutableStateFlow<UiState<Int>>(UiState.Empty)
        val itemLikeMinusState: StateFlow<UiState<Int>> = _itemLikeMinusState

        fun getHomeDataFromServer() {
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

        fun setLikeStateWithServer(
            productId: String,
            isInterested: Boolean,
            position: Int,
        ) {
            viewModelScope.launch {
                if (isInterested) {
                    interestRepository.deleteInterest(productId)
                        .onSuccess {
                            _itemLikeMinusState.value = UiState.Success(position)
                        }
                        .onFailure {
                            _itemLikeMinusState.value = UiState.Failure(it.message.orEmpty())
                        }
                } else {
                    interestRepository.postInterest(productId)
                        .onSuccess {
                            _itemLikePlusState.value = UiState.Success(position)
                        }
                        .onFailure {
                            _itemLikePlusState.value = UiState.Failure(it.message.orEmpty())
                        }
                }
            }
        }

        fun resetLikeState() {
            _itemLikePlusState.value = UiState.Empty
            _itemLikeMinusState.value = UiState.Empty
        }

        fun getUserLogined() = userRepository.getAccessToken().isNotEmpty()

        fun setDeviceToken(deviceToken: String) {
            userRepository.setDeviceToken(deviceToken)
        }
    }
