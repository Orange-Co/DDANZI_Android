package co.orange.domain.repository

import co.orange.domain.entity.response.BuyProgressModel

interface BuyRepository {
    suspend fun getButProgressData(productId: String): Result<BuyProgressModel>
}
