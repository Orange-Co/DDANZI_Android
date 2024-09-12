package co.orange.data.dto.response

import co.orange.domain.entity.response.PageInfoModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PageInfoDto(
    @SerialName("totalElements")
    val totalElements: Long,
    @SerialName("numberOfElements")
    val numberOfElements: Int,
) {
    fun toModel() = PageInfoModel(totalElements, numberOfElements)
}
