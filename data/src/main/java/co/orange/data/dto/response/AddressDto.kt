package co.orange.data.dto.response

import co.orange.domain.entity.response.AddressModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressDto(
    @SerialName("addressId")
    val addressId: Long?,
    @SerialName("recipient")
    val recipient: String?,
    @SerialName("zipCode")
    val zipCode: String?,
    @SerialName("type")
    val type: String?,
    @SerialName("address")
    val address: String,
    @SerialName("detailAddress")
    val detailAddress: String,
    @SerialName("recipientPhone")
    val recipientPhone: String?,
) {
    fun toModel() = AddressModel(addressId, recipient, zipCode, type, address, detailAddress, recipientPhone)
}
