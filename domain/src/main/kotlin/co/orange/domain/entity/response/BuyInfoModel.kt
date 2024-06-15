package co.orange.domain.entity.response

data class BuyInfoModel(
    val productName: String,
    val imgUrl: String,
    val originPrice: Int,
    val addressInfo: List<AddressInfoModel>,
    val discountPrice: Int,
    val charge: Int,
    val totalPrice: Int,
)