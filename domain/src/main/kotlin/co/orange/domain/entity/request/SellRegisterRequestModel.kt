package co.orange.domain.entity.request

data class SellRegisterRequestModel(
    val productId: String,
    val productName: String,
    val receivedDate: String,
    val registeredImage: String,
)
