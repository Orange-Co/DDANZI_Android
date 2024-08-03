package co.orange.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IamportBaseResponse<T>(
    @SerialName("code")
    val code: Int?,
    @SerialName("message")
    val message: String?,
    @SerialName("response")
    val response: T?,
)
