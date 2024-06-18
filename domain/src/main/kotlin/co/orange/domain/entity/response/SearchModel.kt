package co.orange.domain.entity.response

data class SearchModel(
    val topSearchedList: List<String>,
    val recentViewedList: List<ProductModel>,
)