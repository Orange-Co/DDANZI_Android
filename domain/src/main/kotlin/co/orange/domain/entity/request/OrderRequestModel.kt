package co.orange.domain.entity.request

data class OrderRequestModel(
    val itemId: String,
    val orderId: String,
    val selectedOptionDetailIdList: List<Long>,
)
