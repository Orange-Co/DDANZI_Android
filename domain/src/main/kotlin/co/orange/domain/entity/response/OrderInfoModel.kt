package co.orange.domain.entity.response

data class OrderInfoModel(
    val orderId: String,
    val orderStatus: String,
    val productName: String,
    val imgUrl: String,
    val originPrice: Int,
    val addressInfo: AddressInfoModel,
    val sellerNickname: String,
    val paymentMethod: String,
    val paidAt: String,
    val discountPrice: Int,
    val charge: Int,
    val totalPrice: Int,
)
