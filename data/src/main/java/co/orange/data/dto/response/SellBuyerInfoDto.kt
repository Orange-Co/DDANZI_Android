package co.orange.data.dto.response

import co.orange.domain.entity.response.SellBuyerInfoModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SellBuyerInfoDto(
    @SerialName("address")
    val address: String,
    @SerialName("detailAddress")
    val detailAddress: String,
    @SerialName("zipcode")
    val zipCode: String,
    @SerialName("recipient")
    val recipient: String,
    @SerialName("recipientPhone")
    val recipientPhone: String,
    @SerialName("selectedOptionList")
    val selectedOptionList: List<String>,
) {
    fun toModel() = SellBuyerInfoModel(address, detailAddress, zipCode, recipient, recipientPhone, selectedOptionList)
}
