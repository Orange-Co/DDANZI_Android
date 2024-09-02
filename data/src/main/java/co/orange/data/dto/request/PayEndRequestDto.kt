package co.orange.data.dto.request

import co.orange.domain.entity.request.PayEndRequestModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PayEndRequestDto(
    @SerialName("orderId")
    val orderId: String,
    @SerialName("payStatus")
    val payStatus: String,
) {
    companion object {
        fun PayEndRequestModel.toDto() = PayEndRequestDto(orderId, payStatus)
    }
}
