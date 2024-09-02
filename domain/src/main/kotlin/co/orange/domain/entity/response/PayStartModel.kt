package co.orange.domain.entity.response

data class PayStartModel(
    val orderId: String,
    val payStatus: String,
    val startedAt: String,
)
