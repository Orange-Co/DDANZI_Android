package co.orange.domain.entity.response

data class BuyInfoModel(
    val addressInfo: List<AddressInfoModel>,
    val paymentInfo: List<PaymentInfoModel>,
    val originPrice: Int,
    val discountPrice: Int,
    val charge: Int,
    val totalPrice: Int,
)