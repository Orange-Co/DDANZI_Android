package co.orange.data.dto.response

import co.orange.domain.entity.response.AuthTokenModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthTokenDto(
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String,
    @SerialName("userId")
    val userId: Long,
) {
    fun toModel() = AuthTokenModel(accessToken = accessToken, refreshToken = refreshToken, userId = userId)
}
