package co.orange.domain.entity.response

data class SearchInfoModel(
    val topSearchedList: List<String>,
    val recentlyViewedList: List<ProductModel>,
)
