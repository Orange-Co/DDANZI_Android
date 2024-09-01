package co.orange.domain.repository

import co.orange.domain.entity.response.SignedUrlModel

interface SellRepository {
    suspend fun getSignedUrl(fileName: String): Result<SignedUrlModel>
}
