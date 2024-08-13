package co.orange.data.dto.response

import co.orange.domain.entity.response.AuthTokenModel
import kotlinx.serialization.SerialName

data class AuthTokenDto(
    @SerialName("accesstoken")
    val accesstoken: String,
    @SerialName("refreshtoken")
    val refreshtoken: String,
    @SerialName("nickname")
    val nickname: String,
) {
    fun toModel() = AuthTokenModel(accesstoken, refreshtoken, nickname)
}
