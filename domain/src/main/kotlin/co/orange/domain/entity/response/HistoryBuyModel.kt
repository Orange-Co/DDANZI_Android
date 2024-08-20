package co.orange.domain.entity.response

data class HistoryBuyModel(
    val totalCount: Int,
    val orderProductList: List<OrderProductModel>,
) {
    data class OrderProductModel(
        val productId: String,
        val orderId: String,
        val productName: String,
        val imgUrl: String,
        val originPrice: Int,
        val salePrice: Int,
        val paidAt: String,
    )
}
