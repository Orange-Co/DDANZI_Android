package co.orange.domain.entity.response

data class BuyProgressModel(
    val itemId: String,
    val productName: String,
    val imgUrl: String,
    val originPrice: Int,
    val addressInfo: AddressInfoModel,
    val discountPrice: Int,
    val charge: Int,
    val totalPrice: Int,
)
