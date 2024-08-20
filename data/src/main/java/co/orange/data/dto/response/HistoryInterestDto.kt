package co.orange.data.dto.response

import co.orange.domain.entity.response.HistoryInterestModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HistoryInterestDto(
    @SerialName("totalCount")
    val totalCount: Int,
    @SerialName("productList")
    val productList: List<ProductDto>,
) {
    fun toModel() = HistoryInterestModel(totalCount, productList.map { it.toModel() })
}
