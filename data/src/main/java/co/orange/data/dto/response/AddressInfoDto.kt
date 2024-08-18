package co.orange.data.dto.response

import co.orange.domain.entity.response.AddressInfoModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressInfoDto(
    @SerialName("recipient")
    val recipient: String? = null,
    @SerialName("zipCode")
    val zipCode: String? = null,
    @SerialName("address")
    val address: String? = null,
    @SerialName("recipientPhone")
    val recipientPhone: String? = null,
) {
    fun toModel() = AddressInfoModel(recipient, zipCode, address, recipientPhone)
}
