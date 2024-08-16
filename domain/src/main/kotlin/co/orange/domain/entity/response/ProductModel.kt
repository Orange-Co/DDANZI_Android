package co.orange.domain.entity.response

data class ProductModel(
    val productId: String,
    val kakaoProductId: Long,
    val name: String,
    val imgUrl: String,
    val originPrice: Int,
    val salePrice: Int,
    var isInterested: Boolean,
    var interestCount: Int,
)
