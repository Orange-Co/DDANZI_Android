package co.orange.data.dto.response

import co.orange.domain.entity.response.SellRegisteredModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SellRegisteredDto(
    @SerialName("itemId")
    val itemId: String,
    @SerialName("productName")
    val productName: String,
    @SerialName("salePrice")
    val salePrice: Int,
) {
    fun toModel() = SellRegisteredModel(itemId, productName, salePrice)
}
