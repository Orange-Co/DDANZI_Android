package co.orange.data.dto.response

import co.orange.domain.entity.response.PayStartModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PayStartDto(
    @SerialName("paymentId")
    val paymentId: Long,
    @SerialName("payStatus")
    val payStatus: String,
    @SerialName("startedAt")
    val startedAt: String,
) {
    fun toModel() =
        PayStartModel(
            paymentId = paymentId,
            payStatus = payStatus,
            startedAt = startedAt,
        )
}
