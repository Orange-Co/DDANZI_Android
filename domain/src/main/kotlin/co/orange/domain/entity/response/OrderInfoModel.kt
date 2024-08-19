package co.orange.domain.entity.response

import co.orange.domain.enums.OrderStatus

data class OrderInfoModel(
    val orderId: String,
    val orderStatus: OrderStatus,
    val productName: String,
    val imgUrl: String,
    val originPrice: Int,
    val addressInfo: List<AddressInfoModel>,
    val sellerNickname: String,
    val paymentMethod: String,
    val paidAt: String,
    val discountPrice: Int,
    val charge: Int,
    val totalPrice: Int,
)
