package co.orange.domain.entity.response

data class PayEndModel(
    val paymentId: String,
    val payStatus: String,
    val endedAt: String,
)
