package co.orange.data.dataSource

import co.orange.data.dto.BaseResponse
import co.orange.data.dto.response.ProductDetailDto
import co.orange.data.dto.response.SearchInfoDto

interface DeviceDataSource {
    suspend fun getProductDetail(id: String): BaseResponse<ProductDetailDto>

    suspend fun getSearchInfo(): BaseResponse<SearchInfoDto>
}
