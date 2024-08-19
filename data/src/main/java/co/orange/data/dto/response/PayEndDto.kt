package co.orange.data.dto.response

import co.orange.domain.entity.response.PayEndModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PayEndDto(
    @SerialName("paymentId")
    val paymentId: Long,
    @SerialName("payStatus")
    val payStatus: String,
    @SerialName("endedAt")
    val endedAt: String,
) {
    fun toModel() =
        PayEndModel(
            paymentId = paymentId,
            payStatus = payStatus,
            endedAt = endedAt,
        )
}
