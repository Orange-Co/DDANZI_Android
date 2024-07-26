package co.orange.data.dto.response

import co.orange.domain.entity.response.InterestModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InterestDto(
    @SerialName("nickname")
    val nickname: String,
    @SerialName("productId")
    val productId: Long,
) {
    fun toModel() =
        InterestModel(
            nickname,
            productId,
        )
}
