package co.orange.data.dataSource

import co.orange.data.dto.BaseResponse
import co.orange.data.dto.response.SignedUrlDto

interface SellDataSource {
    suspend fun getSignedUrl(fileName: String): BaseResponse<SignedUrlDto>
}
