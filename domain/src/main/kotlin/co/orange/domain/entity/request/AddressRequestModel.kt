package co.orange.domain.entity.request

data class AddressRequestModel(
    val recipient: String,
    val zipCode: String,
    val type: String,
    val address: String,
    val detailAddress: String,
    val recipientPhone: String,
)
