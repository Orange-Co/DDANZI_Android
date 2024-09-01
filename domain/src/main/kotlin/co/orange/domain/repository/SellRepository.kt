package co.orange.domain.repository

import co.orange.domain.entity.request.SellCheckRequestModel
import co.orange.domain.entity.response.SellCheckedProductModel
import co.orange.domain.entity.response.SignedUrlModel

interface SellRepository {
    suspend fun getSignedUrl(fileName: String): Result<SignedUrlModel>

    suspend fun postToCheckProduct(request: SellCheckRequestModel): Result<SellCheckedProductModel>
}
