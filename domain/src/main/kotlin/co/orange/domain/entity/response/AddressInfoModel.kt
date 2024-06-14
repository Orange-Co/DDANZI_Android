package co.orange.domain.entity.response

data class AddressInfoModel(
    val recipient: String,
    val zipCode: Int,
    val address: String,
    val phone: String
)