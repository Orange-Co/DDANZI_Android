package co.orange.data.dataSource

import co.orange.data.dto.BaseResponse
import co.orange.data.dto.response.ProductDetailDto

interface DetailDataSource {
    suspend fun getHomeData(id: String): BaseResponse<ProductDetailDto>
}
