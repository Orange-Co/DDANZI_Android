package co.orange.data.dto.response

import co.orange.domain.entity.response.ProfileNicknameModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileNicknameDto(
    @SerialName("nickname")
    val nickname: String,
) {
    fun toModel() = ProfileNicknameModel(nickname)
}
