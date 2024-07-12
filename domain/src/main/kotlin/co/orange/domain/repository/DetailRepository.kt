package co.orange.domain.repository

import co.orange.domain.entity.response.ProductDetailModel

interface DetailRepository {
    suspend fun getProductDetail(id: String): Result<ProductDetailModel>
}
