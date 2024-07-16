package co.orange.data.dataSource

import co.orange.data.dto.BaseResponse
import co.orange.data.dto.response.ProductDetailDto

interface DeviceDataSource {
    suspend fun getProductDetail(id: String): BaseResponse<ProductDetailDto>
}
