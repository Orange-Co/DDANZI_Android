package co.orange.data.dataSource

import co.orange.data.dto.BaseResponse
import co.orange.data.dto.response.InterestDto

interface InterestDataSource {
    suspend fun postInterest(productId: String): BaseResponse<InterestDto>

    suspend fun deleteInterest(productId: String): BaseResponse<InterestDto>
}
