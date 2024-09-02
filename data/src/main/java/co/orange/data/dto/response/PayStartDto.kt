package co.orange.data.dto.response

import co.orange.domain.entity.response.PayStartModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PayStartDto(
    @SerialName("orderId")
    val orderId: String,
    @SerialName("payStatus")
    val payStatus: String,
    @SerialName("startedAt")
    val startedAt: String,
) {
    fun toModel() =
        PayStartModel(
            orderId = orderId,
            payStatus = payStatus,
            startedAt = startedAt,
        )
}
