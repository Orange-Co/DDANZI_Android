package co.orange.data.dto.response

import co.orange.domain.entity.response.HomeModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeDto(
    @SerialName("homeImgUrl")
    val homeImgUrl: String,
    @SerialName("productList")
    val productList: List<ProductDto>,
    @SerialName("pageInfo")
    val pageInfo: PageInfoDto,
) {
    fun toModel() = HomeModel(homeImgUrl, productList.map { it.toModel() }, pageInfo.toModel())
}
