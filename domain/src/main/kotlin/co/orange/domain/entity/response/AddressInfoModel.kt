package co.orange.domain.entity.response

data class AddressInfoModel(
    val recipient: String,
    val zipCode: String,
    val address: String,
    val phone: String
)