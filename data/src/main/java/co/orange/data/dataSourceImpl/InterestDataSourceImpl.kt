package co.orange.data.dataSourceImpl

import co.orange.data.dataSource.InterestDataSource
import co.orange.data.dto.BaseResponse
import co.orange.data.dto.response.InterestDto
import javax.inject.Inject

data class InterestDataSourceImpl
    @Inject
    constructor(
        private val interestDataSource: InterestDataSource,
    ) : InterestDataSource {
        override suspend fun postInterest(productId: String): BaseResponse<InterestDto> = interestDataSource.postInterest(productId)

        override suspend fun deleteInterest(productId: String): BaseResponse<InterestDto> = interestDataSource.deleteInterest(productId)
    }
