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
                    OptionModel(
                        1,
                        "색상",
                        listOf(
                            OptionModel.OptionDetailModel(0, "네이비", true),
                            OptionModel.OptionDetailModel(1, "블랙", true),
                        ),
                    ),
                    OptionModel(
                        2,
                        "사이즈",
                        listOf(
                            OptionModel.OptionDetailModel(0, "S", true),
                            OptionModel.OptionDetailModel(1, "M", true),
                            OptionModel.OptionDetailModel(2, "L", true),
                            OptionModel.OptionDetailModel(3, "XL", true),
                        ),
                    ),
                ),
            )
    }
