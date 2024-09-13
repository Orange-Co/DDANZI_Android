package co.orange.domain.entity.response

data class HomeModel(
    val homeImgUrl: String,
    val productList: List<ProductModel>,
    val pageInfo: PageInfoModel,
)
