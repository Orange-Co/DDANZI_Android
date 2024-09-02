package co.orange.domain.entity.response

data class SellProductModel(
    val productId: String,
    val productName: String,
    val originPrice: Int,
    val salePrice: Int,
    val isAccountExist: Boolean,
    val imgUrl: String,
)
