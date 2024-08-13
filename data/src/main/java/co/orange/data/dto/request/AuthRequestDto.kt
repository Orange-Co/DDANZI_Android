package co.orange.data.dto.request

import co.orange.domain.entity.request.AuthRequestModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthRequestDto(
    @SerialName("token")
    val token: String,
    @SerialName("type")
    val type: String,
) {
    companion object {
        fun AuthRequestModel.toDto() = AuthRequestDto(token, type)
    }
}
