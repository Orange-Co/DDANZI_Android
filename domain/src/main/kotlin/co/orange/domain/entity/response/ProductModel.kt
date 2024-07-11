package co.orange.domain.entity.response

data class ProductModel(
    val productId: Long,
    val kakaoProductId: Long,
    val name: String,
    val imgUrl: String,
    val originPrice: Int,
    val salePrice: Int,
    val interestCount: Int,
)
