package co.orange.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IamportTokenRequestDto(
    @SerialName("imp_key")
    val impKey: String,
    @SerialName("imp_secret")
    val impSecret: String,
)
