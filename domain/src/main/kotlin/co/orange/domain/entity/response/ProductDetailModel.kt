package co.orange.domain.entity.response

data class ProductDetailModel(
    val name: String,
    val category: String,
    val isOptionExist: Boolean,
    val isImminent: Boolean,
    val discountRate: Int,
    val stockCount: Int,
    val infoUrl: String,
    val interestCount: Int,
    val optionList: List<OptionModel>
)