package co.orange.data.dto.response

import co.orange.domain.entity.response.SellCheckedProductModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SellCheckedProductDto(
    @SerialName("productId")
    val productId: String,
    @SerialName("productName")
    val productName: String,
) {
    fun toModel() = SellCheckedProductModel(productId, productName)
}
