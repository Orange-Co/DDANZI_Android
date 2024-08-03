package co.orange.data.dto.request

import co.orange.domain.entity.request.IamportTokenRequestModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IamportTokenRequestDto(
    @SerialName("imp_key")
    val impKey: String,
    @SerialName("imp_secret")
    val impSecret: String,
) {
    companion object {
        fun IamportTokenRequestModel.toDto() = IamportTokenRequestDto(impKey, impSecret)
    }
}
