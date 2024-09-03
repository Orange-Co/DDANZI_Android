package co.orange.data.dto.response

import co.orange.domain.entity.response.SellInfoModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SellInfoDto(
    @SerialName("itemId")
    val itemId: String,
    @SerialName("status")
    val status: String,
    @SerialName("productName")
    val productName: String,
    @SerialName("imgUrl")
    val imgUrl: String,
    @SerialName("originPrice")
    val originPrice: Int,
    @SerialName("salePrice")
    val salePrice: Int,
    @SerialName("orderId")
    val orderId: String,
    @SerialName("buyerNickname")
    val buyerNickname: String,
    @SerialName("paidAt")
    val paidAt: String,
    @SerialName("addressInfo")
    val addressInfo: AddressInfoDto,
    @SerialName("paymentMethod")
    val paymentMethod: String,
) {
    fun toModel() =
        SellInfoModel(
            itemId,
            status,
            productName,
            imgUrl,
            originPrice,
            salePrice,
            orderId,
            buyerNickname,
            paidAt,
            addressInfo.toModel(),
            paymentMethod,
        )
}
