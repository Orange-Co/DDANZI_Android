package co.orange.data.dto.response

import co.orange.domain.entity.response.AddressModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressDto(
    @SerialName("addressId")
    val addressId: Long? = null,
    @SerialName("recipient")
    val recipient: String? = null,
    @SerialName("zipCode")
    val zipCode: String? = null,
    @SerialName("type")
    val type: String? = null,
    @SerialName("address")
    val address: String? = null,
    @SerialName("detailAddress")
    val detailAddress: String? = null,
    @SerialName("recipientPhone")
    val recipientPhone: String? = null,
) {
    fun toModel() = AddressModel(addressId, recipient, zipCode, type, address, detailAddress, recipientPhone)
}
