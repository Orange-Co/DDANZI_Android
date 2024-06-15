package co.orange.presentation.detail

import androidx.lifecycle.ViewModel
import co.orange.domain.entity.response.OptionModel
import co.orange.domain.entity.response.ProductDetailModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
@Inject
constructor(
    // private val feedRepository: FeedRepository,
) : ViewModel() {
    var productId: Long = -1
    var imageUrl: String = ""
    var originPrice: Int = 0
    var salePrice: Int = 0

    val mockProduct = ProductDetailModel(
        "퓨어 오일 퍼퓸 10 ml 긴제목테스트트트트트",
        "카테고리",
        false,
        true,
        30,
        12,
        "",
        123,
        listOf(
            OptionModel(1, "옵션 1", listOf()),
            OptionModel(2, "옵션 2", listOf()),
            OptionModel(3, "옵션 3", listOf()),
            OptionModel(4, "옵션 4", listOf())
        )
    )
}