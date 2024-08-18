package co.orange.domain.entity.response

import co.orange.domain.enums.OrderStatus

data class BuyInfoModel(
    val orderId: String,
    val orderStatus: OrderStatus,
    val productName: String,
    val imgUrl: String,
    val originPrice: Int,
    val sellerNickname: String,
    val addressInfo: List<AddressInfoModel>,
    val paymentInfo: List<PaymentInfoModel>,
    val discountPrice: Int,
    val charge: Int,
    val totalPrice: Int,
)
