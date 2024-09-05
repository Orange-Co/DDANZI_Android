package co.orange.domain.entity.response

data class HistorySellModel(
    val totalCount: Int,
    val itemProductList: List<ItemProductModel>,
) {
    data class ItemProductModel(
        val productId: String,
        val itemId: String,
        val productName: String,
        val imgUrl: String,
        val originPrice: Int,
        val salePrice: Int,
        val isInterested: Boolean,
        val interestCount: Int,
    )
}
