package co.orange.domain.entity.response

data class PayEndModel(
    val paymentId: Long,
    val payStatus: String,
    val endedAt: String,
)
