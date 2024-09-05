package co.orange.data.dto.response

import co.orange.domain.entity.response.HistorySellModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HistorySellDto(
    @SerialName("totalCount")
    val totalCount: Int,
    @SerialName("itemProductList")
    val itemProductList: List<ItemProductDto>,
) {
    @Serializable
    data class ItemProductDto(
        @SerialName("productId")
        val productId: String,
        @SerialName("itemId")
        val itemId: String,
        @SerialName("productName")
        val productName: String,
        @SerialName("imgUrl")
        val imgUrl: String,
        @SerialName("originPrice")
        val originPrice: Int,
        @SerialName("salePrice")
        val salePrice: Int,
        @SerialName("isInterested")
        val isInterested: Boolean,
        @SerialName("interestCount")
        val interestCount: Int,
    ) {
        fun toModel() =
            HistorySellModel.ItemProductModel(
                productId,
                itemId,
                productName,
                imgUrl,
                originPrice,
                salePrice,
                isInterested,
                interestCount,
            )
    }

    fun toModel() = HistorySellModel(totalCount, itemProductList.map { it.toModel() })
}
