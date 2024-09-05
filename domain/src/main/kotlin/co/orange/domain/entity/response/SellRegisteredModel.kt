package co.orange.domain.entity.response

data class SellRegisteredModel(
    val itemId: String,
    val productName: String,
    val salePrice: Int,
    val imgUrl: String,
)
