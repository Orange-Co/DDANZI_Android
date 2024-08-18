package co.orange.data.dto.response

import co.orange.domain.entity.response.AddressInfoModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressInfoDto(
    @SerialName("recipient")
    val recipient: String,
    @SerialName("zipCode")
    val zipCode: String,
    @SerialName("address")
    val address: String,
    @SerialName("phone")
    val phone: String,
) {
    fun toModel() = AddressInfoModel(recipient, zipCode, address, phone)
}
