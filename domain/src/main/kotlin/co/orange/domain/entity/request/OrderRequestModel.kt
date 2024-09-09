package co.orange.domain.entity.request

data class OrderRequestModel(
    val orderId: String,
    val selectedOptionDetailIdList: List<Long>,
)
