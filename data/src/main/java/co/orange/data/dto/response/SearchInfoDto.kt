package co.orange.data.dto.response

import co.orange.domain.entity.response.SearchInfoModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchInfoDto(
    @SerialName("topSearchedList")
    val topSearchedList: List<String>,
    @SerialName("recentlyViewedList")
    val recentlyViewedList: List<ProductDto>,
) {
    fun toModel() = SearchInfoModel(topSearchedList, recentlyViewedList.map { it.toModel() })
}
