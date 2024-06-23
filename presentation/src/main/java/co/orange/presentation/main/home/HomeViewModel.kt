package co.orange.presentation.main.home

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.orange.domain.entity.response.ProductModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        // private val feedRepository: FeedRepository,
    ) : ViewModel() {
        var selectedImageUri = Uri.EMPTY

        private val _isCheckedAgain = MutableSharedFlow<Boolean>()
        val isCheckedAgain: SharedFlow<Boolean> = _isCheckedAgain

        fun setCheckedState(state: Boolean) {
            viewModelScope.launch {
                _isCheckedAgain.emit(state)
            }
        }

        val mockItemList =
            listOf(
                ProductModel(
                    1,
                    "퓨어 오일 퍼퓸 10 ml 긴제목테스트트트트트",
                    "https://github.com/Marchbreeze/Marchbreeze/assets/97405341/cd2c0454-92b4-41e7-ae2f-319f83e2426f",
                    54000,
                    48900,
                    12,
                ),
                ProductModel(
                    2,
                    "퓨어 오일 퍼퓸 10 ml 긴제목테스트트트트트",
                    "https://github.com/Marchbreeze/Marchbreeze/assets/97405341/cd2c0454-92b4-41e7-ae2f-319f83e2426f",
                    54000,
                    48900,
                    34,
                ),
                ProductModel(
                    3,
                    "퓨어 오일 퍼퓸 10 ml 긴제목테스트트트트트",
                    "https://github.com/Marchbreeze/Marchbreeze/assets/97405341/cd2c0454-92b4-41e7-ae2f-319f83e2426f",
                    54000,
                    48900,
                    56,
                ),
                ProductModel(
                    4,
                    "퓨어 오일 퍼퓸 10 ml 긴제목테스트트트트트",
                    "https://github.com/Marchbreeze/Marchbreeze/assets/97405341/cd2c0454-92b4-41e7-ae2f-319f83e2426f",
                    54000,
                    48900,
                    78,
                ),
                ProductModel(
                    5,
                    "퓨어 오일 퍼퓸 10 ml 긴제목테스트트트트트",
                    "https://github.com/Marchbreeze/Marchbreeze/assets/97405341/cd2c0454-92b4-41e7-ae2f-319f83e2426f",
                    54000,
                    48900,
                    910,
                ),
                ProductModel(
                    6,
                    "퓨어 오일 퍼퓸 10 ml 긴제목테스트트트트트",
                    "https://github.com/Marchbreeze/Marchbreeze/assets/97405341/cd2c0454-92b4-41e7-ae2f-319f83e2426f",
                    54000,
                    48900,
                    112,
                ),
                ProductModel(
                    7,
                    "퓨어 오일 퍼퓸 10 ml 긴제목테스트트트트트",
                    "https://github.com/Marchbreeze/Marchbreeze/assets/97405341/cd2c0454-92b4-41e7-ae2f-319f83e2426f",
                    54000,
                    48900,
                    1234,
                ),
                ProductModel(
                    8,
                    "퓨어 오일 퍼퓸 10 ml 긴제목테스트트트트트",
                    "https://github.com/Marchbreeze/Marchbreeze/assets/97405341/cd2c0454-92b4-41e7-ae2f-319f83e2426f",
                    54000,
                    48900,
                    1234,
                ),
                ProductModel(
                    9,
                    "퓨어 오일 퍼퓸 10 ml 긴제목테스트트트트트",
                    "https://github.com/Marchbreeze/Marchbreeze/assets/97405341/cd2c0454-92b4-41e7-ae2f-319f83e2426f",
                    54000,
                    48900,
                    1234,
                ),
            )
    }
