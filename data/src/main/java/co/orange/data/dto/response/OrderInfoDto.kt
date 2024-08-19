package co.orange.data.dto.response

import co.orange.domain.entity.response.OrderInfoModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderInfoDto(
    @SerialName("orderId")
    val orderId: String,
    @SerialName("orderStatus")
    val orderStatus: String,
    @SerialName("productName")
    val productName: String,
    @SerialName("imgUrl")
    val imgUrl: String,
    @SerialName("originPrice")
    val originPrice: Int,
    @SerialName("addressInfo")
    val addressInfo: AddressInfoDto,
    @SerialName("sellerNickname")
    val sellerNickname: String,
    @SerialName("paymentMethod")
    val paymentMethod: String,
    @SerialName("paidAt")
    val paidAt: String,
    @SerialName("discountPrice")
    val discountPrice: Int,
    @SerialName("charge")
    val charge: Int,
    @SerialName("totalPrice")
    val totalPrice: Int,
) {
    fun toModel() =
        OrderInfoModel(
            orderId = orderId,
            orderStatus = orderStatus,
            productName = productName,
            imgUrl = imgUrl,
            originPrice = originPrice,
            addressInfo = addressInfo.toModel(),
            sellerNickname = sellerNickname,
            paymentMethod = paymentMethod,
            paidAt = paidAt,
            discountPrice = discountPrice,
            charge = charge,
            totalPrice = totalPrice,
        )
}
