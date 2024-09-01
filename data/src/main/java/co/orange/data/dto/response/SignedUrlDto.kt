package co.orange.data.dto.response

import co.orange.domain.entity.response.SignedUrlModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignedUrlDto(
    @SerialName("signedUrl")
    val signedUrl: String,
) {
    fun toModel() = SignedUrlModel(signedUrl)
}
