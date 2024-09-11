package co.orange.data.dto.request

import co.orange.domain.entity.request.SellRegisterRequestModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SellRegisterRequestDto(
    @SerialName("productId")
    val productId: String,
    @SerialName("productName")
    val productName: String,
    @SerialName("receivedDate")
    val receivedDate: String,
    @SerialName("registeredImage")
    val registeredImage: String,
) {
    companion object {
        fun SellRegisterRequestModel.toDto() = SellRegisterRequestDto(productId, productName, receivedDate, registeredImage)
    }
}
