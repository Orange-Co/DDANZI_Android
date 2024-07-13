package co.orange.data.dataSourceImpl

import co.orange.data.dataSource.DetailDataSource
import co.orange.data.dto.BaseResponse
import co.orange.data.dto.response.ProductDetailDto
import co.orange.data.service.DetailService
import javax.inject.Inject

data class DetailDataSourceImpl
    @Inject
    constructor(
        private val detailService: DetailService,
    ) : DetailDataSource {
        override suspend fun getHomeData(id: String): BaseResponse<ProductDetailDto> = detailService.getProductDetail(id)
    }
