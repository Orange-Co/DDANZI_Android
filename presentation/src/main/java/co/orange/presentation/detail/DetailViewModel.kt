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
    val mockProduct = ProductDetailModel(
        "퓨어 오일 퍼퓸 10ml 입니다 긴제목은 어떻게 될까요? 짧은 것도 이간격",
        "카테고리",
        false,
        true,
        30,
        12,
        "",
        123,
        listOf(
            OptionModel(1,"옵션 1", listOf()),
            OptionModel(2,"옵션 2", listOf()),
            OptionModel(3,"옵션 3", listOf()),
            OptionModel(4,"옵션 4", listOf())
        )
    )
}