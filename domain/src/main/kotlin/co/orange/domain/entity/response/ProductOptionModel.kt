package co.orange.domain.entity.response

data class ProductOptionModel(
    val optionId: Long,
    val type: String,
    val optionDetailList: List<OptionDetailModel>,
) {
    data class OptionDetailModel(
        val optionDetailId: Long,
        val content: String,
        val isAvailable: Boolean,
    )
}
