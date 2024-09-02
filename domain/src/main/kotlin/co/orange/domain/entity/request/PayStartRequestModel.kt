package co.orange.domain.entity.request

data class PayStartRequestModel(
    val productId: String,
    val charge: Int,
    val totalPrice: Int,
    val method: String,
)
