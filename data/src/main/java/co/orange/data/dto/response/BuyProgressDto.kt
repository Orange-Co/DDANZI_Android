package co.orange.data.dto.response

import co.orange.domain.entity.response.BuyProgressModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BuyProgressDto(
    @SerialName("itemId")
    val itemId: String,
    @SerialName("productName")
    val productName: String,
    @SerialName("modifiedProductName")
    val modifiedProductName: String,
    @SerialName("imgUrl")
    val imgUrl: String,
    @SerialName("originPrice")
    val originPrice: Int,
    @SerialName("addressInfo")
    val addressInfo: AddressInfoDto,
    @SerialName("discountPrice")
    val discountPrice: Int,
    @SerialName("charge")
    val charge: Int,
    @SerialName("totalPrice")
    val totalPrice: Int,
) {
    fun toModel() =
        BuyProgressModel(
            itemId,
            productName,
            modifiedProductName,
            imgUrl,
            originPrice,
            addressInfo.toModel(),
            discountPrice,
            charge,
            totalPrice,
        )
}
