package co.orange.domain.repository

import co.orange.domain.entity.response.InterestModel

interface InterestRepository {
    suspend fun postInterest(productId: Long): Result<InterestModel>

    suspend fun deleteInterest(productId: Long): Result<InterestModel>
}
