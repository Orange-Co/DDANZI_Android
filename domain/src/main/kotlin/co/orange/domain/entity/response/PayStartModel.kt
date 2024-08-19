package co.orange.domain.entity.response

data class PayStartModel(
    val paymentId: Long,
    val payStatus: String,
    val startedAt: String,
)
