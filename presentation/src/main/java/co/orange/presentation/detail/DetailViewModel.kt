package co.orange.presentation.detail

import androidx.lifecycle.ViewModel
import co.orange.domain.entity.response.ProductDetailModel
import co.orange.domain.entity.response.ProductOptionModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
    @Inject
    constructor(
        // private val feedRepository: FeedRepository,
    ) : ViewModel() {
        var productId: String = ""
        var imageUrl: String = ""
        var originPrice: Int = 0
        var salePrice: Int = 0

        val mockProduct =
            ProductDetailModel(
                "퓨어 오일 퍼퓸 10 ml 긴제목테스트트트트트",
                "카테고리",
                false,
                true,
                30,
                12,
                "",
                123,
                listOf(
                    ProductOptionModel(
                        1,
                        "색상",
                        listOf(
                            ProductOptionModel.OptionDetailModel(0, "네이비", true),
                            ProductOptionModel.OptionDetailModel(1, "블랙", true),
                        ),
                    ),
                    ProductOptionModel(
                        2,
                        "사이즈",
                        listOf(
                            ProductOptionModel.OptionDetailModel(0, "S", true),
                            ProductOptionModel.OptionDetailModel(1, "M", true),
                            ProductOptionModel.OptionDetailModel(2, "L", true),
                            ProductOptionModel.OptionDetailModel(3, "XL", true),
                        ),
                    ),
                ),
            )
    }
