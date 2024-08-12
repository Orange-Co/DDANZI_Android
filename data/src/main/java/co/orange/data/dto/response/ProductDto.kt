package co.orange.data.dto.response

import co.orange.domain.entity.response.ProductModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    @SerialName("productId")
    val productId: String,
    @SerialName("kakaoProductId")
    val kakaoProductId: Long,
    @SerialName("name")
    val name: String,
    @SerialName("imgUrl")
    val imgUrl: String,
    @SerialName("originPrice")
    val originPrice: Int,
    @SerialName("salePrice")
    val salePrice: Int,
    @SerialName("isInterested")
    val isInterested: Boolean,
    @SerialName("interestCount")
    val interestCount: Int,
) {
    fun toModel() =
        ProductModel(
            productId,
            kakaoProductId,
            name,
            imgUrl,
            originPrice,
            salePrice,
            isInterested,
            interestCount,
        )
}
