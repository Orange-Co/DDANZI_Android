package co.orange.domain.repository

import co.orange.domain.entity.response.ProductDetailModel
import co.orange.domain.entity.response.SearchInfoModel

interface DeviceRepository {
    suspend fun getProductDetail(id: String): Result<ProductDetailModel>

    suspend fun getSearchInfo(): Result<SearchInfoModel>
}
