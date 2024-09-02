package co.orange.data.dto.request

import co.orange.domain.entity.request.PayStartRequestModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PayStartRequestDto(
    @SerialName("productId")
    val productId: String,
    @SerialName("charge")
    val charge: Int,
    @SerialName("totalPrice")
    val totalPrice: Int,
    @SerialName("method")
    val method: String,
) {
    companion object {
        fun PayStartRequestModel.toDto() = PayStartRequestDto(productId, charge, totalPrice, method)
    }
}
