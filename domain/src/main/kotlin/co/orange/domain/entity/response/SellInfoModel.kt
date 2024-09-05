package co.orange.domain.entity.response

data class SellInfoModel(
    val itemId: String,
    val status: String,
    val productName: String,
    val imgUrl: String,
    val originPrice: Int,
    val salePrice: Int,
    val orderId: String?,
    val buyerNickname: String?,
    val paidAt: String?,
    val addressInfo: AddressInfoModel,
    val paymentMethod: String?,
)
