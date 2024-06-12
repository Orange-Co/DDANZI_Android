package co.orange.presentation.detail

import androidx.lifecycle.ViewModel
import co.orange.domain.entity.response.ProductDetailModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
@Inject
constructor(
    // private val feedRepository: FeedRepository,
) : ViewModel() {
    val mockProduct = ProductDetailModel(
        "퓨어 오일 퍼퓸 10 ml 긴제목테스트트트트트",
        "카테고리",
        false,
        true,
        30,
        12,
        "",
        123,
        listOf()
    )
}