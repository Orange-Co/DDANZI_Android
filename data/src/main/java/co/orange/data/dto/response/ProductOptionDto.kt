package co.orange.data.dto.response

import co.orange.domain.entity.response.ProductOptionModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductOptionDto(
    @SerialName("optionId")
    val optionId: Long,
    @SerialName("type")
    val type: String,
    @SerialName("optionDetailList")
    val optionDetailList: List<OptionDetailDto>,
) {
    @Serializable
    data class OptionDetailDto(
        @SerialName("optionDetailId")
        val optionDetailId: Long,
        @SerialName("content")
        val content: String,
        @SerialName("isAvailable")
        val isAvailable: Boolean,
    ) {
        fun toModel() = ProductOptionModel.OptionDetailModel(optionDetailId, content, isAvailable)
    }

    fun toModel() = ProductOptionModel(optionId, type, optionDetailList.map { it.toModel() })
}
