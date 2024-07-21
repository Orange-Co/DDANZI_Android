package co.orange.domain.entity.response

data class ProductDetailModel(
    val name: String,
    val imgUrl: String,
    val category: String,
    val isImminent: Boolean,
    val isOptionExist: Boolean,
    val discountRate: Int,
    val originPrice: Int,
    val salePrice: Int,
    val stockCount: Int,
    val infoUrl: String,
    val interestCount: Int,
    val optionList: List<ProductOptionModel>,
)
