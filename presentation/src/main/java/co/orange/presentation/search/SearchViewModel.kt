package co.orange.presentation.search

import androidx.lifecycle.ViewModel
import co.orange.domain.entity.response.ProductModel
import co.orange.domain.entity.response.SearchModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
@Inject
constructor(
    // private val feedRepository: FeedRepository,
) : ViewModel() {

    var mockSearchModel = SearchModel(
        listOf("향수", "이승준", "곰인형", "성년의 날 선물", "멀티비타민"),
        listOf(
            ProductModel(
                1,
                "퓨어 오일 퍼퓸 10 ml 긴제목테스트트트트트",
                "https://github.com/Marchbreeze/Marchbreeze/assets/97405341/cd2c0454-92b4-41e7-ae2f-319f83e2426f",
                54000,
                48900,
                12
            ),
            ProductModel(
                2,
                "퓨어 오일 퍼퓸 10 ml 긴제목테스트트트트트",
                "https://github.com/Marchbreeze/Marchbreeze/assets/97405341/cd2c0454-92b4-41e7-ae2f-319f83e2426f",
                54000,
                48900,
                34
            ),
        )
    )
}