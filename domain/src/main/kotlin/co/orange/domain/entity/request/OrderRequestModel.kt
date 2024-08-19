package co.orange.domain.entity.request

data class OrderRequestModel(
    val itemId: String,
    val paymentId: String,
    val selectedOptionDetailIdList: List<Long>,
)
