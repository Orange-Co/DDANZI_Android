package co.orange.data.dataSource

import co.orange.data.dto.BaseResponse
import co.orange.data.dto.response.BuyProgressDto

interface BuyDataSource {
    suspend fun getBuyProgressData(productId: String): BaseResponse<BuyProgressDto>
}
