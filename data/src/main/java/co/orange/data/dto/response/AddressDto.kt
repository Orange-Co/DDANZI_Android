package co.orange.data.dto.response

import co.orange.domain.entity.response.AddressModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressDto(
    @SerialName("addressId")
    val addressId: Long,
    @SerialName("name")
    val name: String,
    @SerialName("zipCode")
    val zipCode: String,
    @SerialName("type")
    val type: String,
    @SerialName("address")
    val address: String,
    @SerialName("detailAddress")
    val detailAddress: String,
    @SerialName("phone")
    val phone: String,
) {
    fun toModel() = AddressModel(addressId, name, zipCode, type, address, detailAddress, phone)
}
