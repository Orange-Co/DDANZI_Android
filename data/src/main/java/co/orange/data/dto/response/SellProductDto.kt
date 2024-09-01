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
    @SerialName("isAddressExist")
    val isAddressExist: Boolean,
) {
    fun toModel() = SellProductModel(productId, productName, originPrice, salePrice, isAddressExist)
}
