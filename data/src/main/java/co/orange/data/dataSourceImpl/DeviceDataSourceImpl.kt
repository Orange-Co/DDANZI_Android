package co.orange.data.dataSourceImpl

import co.orange.data.dataSource.DeviceDataSource
import co.orange.data.dto.BaseResponse
import co.orange.data.dto.response.ProductDetailDto
import co.orange.data.dto.response.SearchInfoDto
import co.orange.data.service.DeviceService
import javax.inject.Inject

data class DeviceDataSourceImpl
    @Inject
    constructor(
        private val deviceService: DeviceService,
    ) : DeviceDataSource {
        override suspend fun getProductDetail(id: String): BaseResponse<ProductDetailDto> = deviceService.getProductDetail(id)

        override suspend fun getSearchInfo(): BaseResponse<SearchInfoDto> = deviceService.getSearchInfo()
    }
