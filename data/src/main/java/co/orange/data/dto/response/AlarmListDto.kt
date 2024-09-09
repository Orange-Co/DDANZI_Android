package co.orange.data.dto.response

import co.orange.domain.entity.response.AlarmListModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlarmListDto(
    @SerialName("alarmList")
    val alarmList: List<AlarmItemDto>,
) {
    @Serializable
    data class AlarmItemDto(
        @SerialName("alarmId")
        val alarmId: Long,
        @SerialName("alarmCase")
        val alarmCase: String,
        @SerialName("title")
        val title: String,
        @SerialName("content")
        val content: String,
        @SerialName("time")
        val time: String,
        @SerialName("isChecked")
        val isChecked: Boolean,
        @SerialName("orderId")
        val orderId: String?,
        @SerialName("itemId")
        val itemId: String?,
    ) {
        fun toModel() =
            AlarmListModel.AlarmItemModel(
                alarmId,
                alarmCase,
                title,
                content,
                time,
                isChecked,
                orderId,
                itemId,
            )
    }

    fun toModel() = AlarmListModel(alarmList.map { it.toModel() })
}
