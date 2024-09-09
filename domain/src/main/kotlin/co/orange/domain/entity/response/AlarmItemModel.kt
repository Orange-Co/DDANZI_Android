package co.orange.domain.entity.response

data class AlarmItemModel(
    val alarmId: Long,
    val alarmCase: String,
    val title: String,
    val content: String,
    val time: String,
    val isChecked: Boolean,
    val orderId: String?,
    val itemId: String?,
)
