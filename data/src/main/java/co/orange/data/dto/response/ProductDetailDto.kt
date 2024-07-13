package co.orange.data.dto.response

import co.orange.domain.entity.response.ProductDetailModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDetailDto(
    @SerialName("name")
    val name: String,
    @SerialName("category")
    val category: String,
    @SerialName("isOptionExist")
    val isOptionExist: Boolean,
    @SerialName("isImminent")
    val isImminent: Boolean,
    @SerialName("discountRate")
    val discountRate: Int,
    @SerialName("stockCount")
    val stockCount: Int,
    @SerialName("infoUrl")
    val infoUrl: String,
    @SerialName("interestCount")
    val interestCount: Int,
    @SerialName("optionList")
    val optionList: List<ProductOptionDto>,
) {
    fun toModel() =
        ProductDetailModel(
            name,
            category,
            isOptionExist,
            isImminent,
            discountRate,
            stockCount,
            infoUrl,
            interestCount,
            optionList.map { it.toModel() },
        )
}
