package co.orange.data.dto.response

import co.orange.domain.entity.response.NicknameModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NicknameDto(
    @SerialName("nickname")
    val nickname: String,
) {
    fun toModel() = NicknameModel(nickname)
}
