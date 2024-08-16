package co.orange.data.dto.response

import co.orange.domain.entity.response.AuthTokenModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthTokenDto(
    @SerialName("accesstoken")
    val accesstoken: String,
    @SerialName("refreshtoken")
    val refreshtoken: String,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("status")
    val status: String,
) {
    fun toModel() = AuthTokenModel(accesstoken, refreshtoken, nickname, status)
}
