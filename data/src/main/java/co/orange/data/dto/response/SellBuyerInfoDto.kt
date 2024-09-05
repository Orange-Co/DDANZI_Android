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
    @SerialName("zipCode")
    val zipCode: String,
    @SerialName("recipient")
    val recipient: String,
    @SerialName("recipientPhone")
    val recipientPhone: String,
    @SerialName("selectedOptionList")
    val selectedOptionList: List<SellBuyerOptionDto>,
) {
    @Serializable
    data class SellBuyerOptionDto(
        @SerialName("option")
        val option: String,
        @SerialName("selectedOption")
        val selectedOption: String,
    ) {
        fun toModel() = SellBuyerInfoModel.SellBuyerOptionModel(option, selectedOption)
    }

    fun toModel() =
        SellBuyerInfoModel(
            address,
            detailAddress,
            zipCode,
            recipient,
            recipientPhone,
            selectedOptionList.map { it.toModel() },
        )
}
