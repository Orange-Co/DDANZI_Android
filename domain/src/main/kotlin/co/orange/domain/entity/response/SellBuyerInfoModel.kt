package co.orange.domain.entity.response

data class SellBuyerInfoModel(
    val address: String,
    val detailAddress: String,
    val zipCode: String,
    val recipient: String,
    val recipientPhone: String,
    val selectedOptionList: List<Any>,
)
