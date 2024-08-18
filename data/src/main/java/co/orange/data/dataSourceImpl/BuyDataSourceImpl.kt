package co.orange.data.dataSourceImpl

import co.orange.data.dataSource.BuyDataSource
import co.orange.data.dto.BaseResponse
import co.orange.data.dto.response.BuyProgressDto
import co.orange.data.service.BuyService
import javax.inject.Inject

data class BuyDataSourceImpl
    @Inject
    constructor(
        private val buyService: BuyService,
    ) : BuyDataSource {
        override suspend fun getBuyProgressData(productId: String): BaseResponse<BuyProgressDto> = buyService.getBuyProgressData(productId)
    }
