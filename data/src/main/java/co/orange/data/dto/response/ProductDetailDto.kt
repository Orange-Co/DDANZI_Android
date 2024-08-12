package co.orange.data.dto.response

import co.orange.domain.entity.response.ProductDetailModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDetailDto(
    @SerialName("name")
    val name: String,
    @SerialName("imgUrl")
    val imgUrl: String,
    @SerialName("category")
    val category: String,
    @SerialName("isImminent")
    val isImminent: Boolean,
    @SerialName("isOptionExist")
    val isOptionExist: Boolean,
    @SerialName("discountRate")
    val discountRate: Int,
    @SerialName("originPrice")
    val originPrice: Int,
    @SerialName("salePrice")
    val salePrice: Int,
    @SerialName("stockCount")
    val stockCount: Int,
    @SerialName("infoUrl")
    val infoUrl: String,
    @SerialName("isInterested")
    val isInterested: Boolean,
    @SerialName("interestCount")
    val interestCount: Int,
    @SerialName("optionList")
    val optionList: List<ProductOptionDto>,
) {
    fun toModel() =
        ProductDetailModel(
            name,
            imgUrl,
            category,
            isImminent,
            isOptionExist,
            discountRate,
            originPrice,
            salePrice,
            stockCount,
            infoUrl,
            isInterested,
            interestCount,
            optionList.map { it.toModel() },
        )
}
