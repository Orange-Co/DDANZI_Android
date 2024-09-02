package co.orange.data.dto.response

import co.orange.domain.entity.response.SellProductModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SellProductDto(
    @SerialName("productId")
    val productId: String,
    @SerialName("productName")
    val productName: String,
    @SerialName("originPrice")
    val originPrice: Int,
    @SerialName("salePrice")
    val salePrice: Int,
    @SerialName("isAccountExist")
    val isAccountExist: Boolean,
    @SerialName("imgUrl")
    val imgUrl: String,
) {
    fun toModel() = SellProductModel(productId, productName, originPrice, salePrice, isAccountExist, imgUrl)
}
