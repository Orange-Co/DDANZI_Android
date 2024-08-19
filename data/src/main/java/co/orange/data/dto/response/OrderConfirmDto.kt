package co.orange.data.dto.response

import co.orange.domain.entity.response.OrderConfirmModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderConfirmDto(
    @SerialName("orderId")
    val orderId: String,
    @SerialName("orderStatus")
    val orderStatus: String,
) {
    fun toModel() = OrderConfirmModel(orderId, orderStatus)
}
