package co.orange.data.dto.response

import co.orange.domain.entity.response.ProfileInterestModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileInterestDto(
    @SerialName("totalCount")
    val totalCount: Int,
    @SerialName("productList")
    val productList: List<ProductDto>,
) {
    fun toModel() = ProfileInterestModel(totalCount, productList.map { it.toModel() })
}
