package co.orange.domain.entity.response

data class AddressModel(
    val addressId: Long?,
    val recipient: String?,
    val zipCode: String?,
    val type: String?,
    val address: String?,
    val detailAddress: String?,
    val recipientPhone: String?,
)
