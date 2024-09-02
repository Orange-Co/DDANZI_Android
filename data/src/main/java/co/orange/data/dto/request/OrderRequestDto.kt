package co.orange.data.dto.request

import co.orange.domain.entity.request.OrderRequestModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderRequestDto(
    @SerialName("itemId")
    val itemId: String,
    @SerialName("orderId")
    val orderId: String,
    @SerialName("selectedOptionDetailIdList")
    val selectedOptionDetailIdList: List<Long>,
) {
    companion object {
        fun OrderRequestModel.toDto() = OrderRequestDto(itemId, orderId, selectedOptionDetailIdList)
    }
}
