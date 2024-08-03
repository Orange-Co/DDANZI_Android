package co.orange.data.dto.response

import co.orange.domain.entity.response.IamportTokenModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IamportTokenDto(
    @SerialName("code")
    val code: Int?,
    @SerialName("message")
    val message: String?,
    @SerialName("response")
    val response: IamportAuthDto?,
) {
    @Serializable
    data class IamportAuthDto(
        @SerialName("access_token")
        val accessToken: String,
        @SerialName("expired_at")
        val expiredAt: Int,
        @SerialName("now")
        val now: Int,
    ) {
        fun toModel() = IamportTokenModel.IamportAuthModel(accessToken, expiredAt, now)
    }

    fun toModel() = IamportTokenModel(code, message, response?.toModel())
}
