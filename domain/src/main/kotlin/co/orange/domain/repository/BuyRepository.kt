package co.orange.domain.repository

import co.orange.domain.entity.response.BuyProgressModel

interface BuyRepository {
    suspend fun getBuyProgressData(productId: String): Result<BuyProgressModel>
}
