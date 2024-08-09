package co.orange.domain.entity.request

data class AddressRequestModel(
    val name: String,
    val zipCode: String,
    val type: String,
    val address: String,
    val detailAddress: String,
    val phone: String,
)
