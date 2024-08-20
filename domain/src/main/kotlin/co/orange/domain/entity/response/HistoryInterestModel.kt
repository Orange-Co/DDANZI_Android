package co.orange.domain.entity.response

data class HistoryInterestModel(
    val totalCount: Int,
    val orderProductList: List<ProductModel>,
)
