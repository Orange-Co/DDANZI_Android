package co.orange.domain.entity.request

data class SellRegisterRequestModel(
    val productId: String,
    val productName: String,
    val dueDate: String,
    val registeredImage: String,
)
