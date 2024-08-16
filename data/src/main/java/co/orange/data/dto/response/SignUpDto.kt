package co.orange.data.dto.response

import co.orange.domain.entity.response.SignUpModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpDto(
    @SerialName("nickname")
    val nickname: String,
    @SerialName("phone")
    val phone: String,
    @SerialName("status")
    val status: String,
) {
    fun toModel() = SignUpModel(nickname, phone, status)
}
