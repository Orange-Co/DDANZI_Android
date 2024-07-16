package co.orange.data.dto.response

import co.orange.domain.entity.response.SettingInfoModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SettingInfoDto(
    @SerialName("name")
    val name: String,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("phone")
    val phone: String,
) {
    fun toModel() = SettingInfoModel(name, nickname, phone)
}
