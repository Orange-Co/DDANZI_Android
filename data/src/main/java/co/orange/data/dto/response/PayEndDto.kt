package co.orange.data.dto.response

import co.orange.domain.entity.response.PayEndModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PayEndDto(
    @SerialName("orderId")
    val orderId: String,
    @SerialName("payStatus")
    val payStatus: String,
    @SerialName("endedAt")
    val endedAt: String,
) {
    fun toModel() =
        PayEndModel(
            orderId = orderId,
            payStatus = payStatus,
            endedAt = endedAt,
        )
}
