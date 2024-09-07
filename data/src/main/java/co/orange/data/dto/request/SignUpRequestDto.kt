package co.orange.data.dto.request

import co.orange.domain.entity.request.SignUpRequestModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequestDto(
    @SerialName("name")
    val name: String,
    @SerialName("phone")
    val phone: String,
    @SerialName("birth")
    val birth: String,
    @SerialName("sex")
    val sex: String,
    @SerialName("isAgreedMarketingTerm")
    val isAgreedMarketingTerm: Boolean,
) {
    companion object {
        fun SignUpRequestModel.toDto() = SignUpRequestDto(name, phone, birth, sex, isAgreedMarketingTerm)
    }
}
