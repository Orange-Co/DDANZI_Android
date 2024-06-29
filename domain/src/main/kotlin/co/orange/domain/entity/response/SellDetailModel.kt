package co.orange.domain.entity.response

import co.orange.domain.enums.ItemStatus
import co.orange.domain.enums.OrderStatus

data class SellDetailModel(
    val itemId: String,
    val itemStatus: ItemStatus,
    val productName: String,
    val imgUrl: String,
    val originPrice: Int,
    val salePrice: Int,
    val orderId: String,
    val orderStatus: OrderStatus,
    val buyerNickname: String,
    val addressInfo: List<AddressInfoModel>,
    val paymentInfo: List<PaymentInfoModel>,
)
