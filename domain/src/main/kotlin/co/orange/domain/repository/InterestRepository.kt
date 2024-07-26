package co.orange.domain.repository

import co.orange.domain.entity.response.InterestModel

interface InterestRepository {
    suspend fun postInterest(productId: String): Result<InterestModel>

    suspend fun deleteInterest(productId: String): Result<InterestModel>
}
