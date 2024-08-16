package co.orange.domain.entity.response

data class AddressModel(
    val addressId: Long? = null,
    val recipient: String? = null,
    val zipCode: String? = null,
    val type: String? = null,
    val address: String? = null,
    val detailAddress: String? = null,
    val recipientPhone: String? = null,
)
