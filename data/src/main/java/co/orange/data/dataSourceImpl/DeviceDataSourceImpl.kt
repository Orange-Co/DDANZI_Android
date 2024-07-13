package co.orange.data.dataSourceImpl

import co.orange.data.dataSource.DeviceDataSource
import co.orange.data.dto.BaseResponse
import co.orange.data.dto.response.ProductDetailDto
import co.orange.data.service.DetailService
import javax.inject.Inject

data class DeviceDataSourceImpl
    @Inject
    constructor(
        private val detailService: DetailService,
    ) : DeviceDataSource {
        override suspend fun getHomeData(id: String): BaseResponse<ProductDetailDto> = detailService.getProductDetail(id)
    }
