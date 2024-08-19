package co.orange.domain.entity.response

data class PayStartModel(
    val paymentId: String,
    val payStatus: String,
    val startedAt: String,
)
