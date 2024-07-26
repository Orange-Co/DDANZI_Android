package co.orange.data.dataSourceImpl

import co.orange.data.dataSource.InterestDataSource
import co.orange.data.dto.BaseResponse
import co.orange.data.dto.response.InterestDto
import co.orange.data.service.InterestService
import javax.inject.Inject

data class InterestDataSourceImpl
    @Inject
    constructor(
        private val interestService: InterestService,
    ) : InterestDataSource {
        override suspend fun postInterest(productId: String): BaseResponse<InterestDto> = interestService.postInterest(productId)

        override suspend fun deleteInterest(productId: String): BaseResponse<InterestDto> = interestService.deleteInterest(productId)
    }
