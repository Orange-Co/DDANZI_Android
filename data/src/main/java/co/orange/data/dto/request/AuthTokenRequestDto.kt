package co.orange.data.dto.request

import co.orange.domain.entity.request.AuthTokenRequestModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthTokenRequestDto(
    @SerialName("userId")
    val userId: Long,
) {
    companion object {
        fun AuthTokenRequestModel.toDto() = AuthTokenRequestDto(userId)
    }
}
