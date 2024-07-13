package co.orange.data.dto.response

import co.orange.domain.entity.response.SearchResultModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResultDto(
    @SerialName("searchedProductList")
    val searchedProductList: List<ProductDto>,
) {
    fun toModel() = SearchResultModel(searchedProductList.map { it.toModel() })
}
