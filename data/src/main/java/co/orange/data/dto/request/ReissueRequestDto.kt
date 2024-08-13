package co.orange.data.dto.request

import co.orange.domain.entity.request.ReissueRequestModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReissueRequestDto(
    @SerialName("userId")
    val userId: Long,
) {
    companion object {
        fun ReissueRequestModel.toDto() = ReissueRequestDto(userId)
    }
}
