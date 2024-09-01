package co.orange.data.dto.request

import co.orange.domain.entity.request.SellCheckRequestModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SellCheckRequestDto(
    @SerialName("image_url")
    val imageUrl: String,
) {
    companion object {
        fun SellCheckRequestModel.toDto() = SellCheckRequestDto(imageUrl)
    }
}
