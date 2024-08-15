package co.orange.data.dto.request

import co.orange.domain.entity.request.AddressRequestModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressRequestDto(
    @SerialName("recipient")
    val recipient: String,
    @SerialName("zipCode")
    val zipCode: String,
    @SerialName("type")
    val type: String,
    @SerialName("address")
    val address: String,
    @SerialName("detailAddress")
    val detailAddress: String,
    @SerialName("recipientPhone")
    val recipientPhone: String,
) {
    companion object {
        fun AddressRequestModel.toDto() = AddressRequestDto(recipient, zipCode, type, address, detailAddress, recipientPhone)
    }
}
