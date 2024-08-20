package co.orange.data.dto.response

import co.orange.domain.entity.response.HistoryBuyModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HistoryBuyDto(
    @SerialName("totalCount")
    val totalCount: Int,
    @SerialName("orderProductList")
    val orderProductList: List<OrderProductDto>,
) {
    @Serializable
    data class OrderProductDto(
        @SerialName("productId")
        val productId: String,
        @SerialName("orderId")
        val orderId: String,
        @SerialName("productName")
        val productName: String,
        @SerialName("imgUrl")
        val imgUrl: String,
        @SerialName("originPrice")
        val originPrice: Int,
        @SerialName("salePrice")
        val salePrice: Int,
        @SerialName("paidAt")
        val paidAt: String,
    ) {
        fun toModel() =
            HistoryBuyModel.OrderProductModel(
                productId = productId,
                orderId = orderId,
                productName = productName,
                imgUrl = imgUrl,
                originPrice = originPrice,
                salePrice = salePrice,
                paidAt = paidAt,
            )
    }

    fun toModel() =
        HistoryBuyModel(
            totalCount = totalCount,
            orderProductList = orderProductList.map { it.toModel() },
        )
}
