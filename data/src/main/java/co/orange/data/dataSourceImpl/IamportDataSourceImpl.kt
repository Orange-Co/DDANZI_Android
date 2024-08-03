package co.orange.data.dataSourceImpl

import co.orange.data.dataSource.IamportDataSource
import co.orange.data.dto.BaseResponse
import co.orange.data.dto.request.IamportTokenRequestDto
import co.orange.data.dto.response.IamportTokenDto
import co.orange.data.service.IamportService
import javax.inject.Inject

data class IamportDataSourceImpl
    @Inject
    constructor(
        private val iamportService: IamportService,
    ) : IamportDataSource {
        override suspend fun postToGetIamportToken(request: IamportTokenRequestDto): BaseResponse<IamportTokenDto> =
            iamportService.postToGetIamportToken(request)
    }
