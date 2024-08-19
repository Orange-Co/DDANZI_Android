package co.orange.data.dto.response

import co.orange.domain.entity.response.OrderIdModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderIdDto(
    @SerialName("orderId")
    val orderId: String,
) {
    fun toModel() = OrderIdModel(orderId)
}
