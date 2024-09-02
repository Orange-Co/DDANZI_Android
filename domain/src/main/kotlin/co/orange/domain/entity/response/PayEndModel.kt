package co.orange.domain.entity.response

data class PayEndModel(
    val orderId: String,
    val payStatus: String,
    val endedAt: String,
)
