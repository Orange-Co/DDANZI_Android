package co.orange.data.dataSourceImpl

import co.orange.data.dataSource.IamportDataSource
import co.orange.data.dto.IamportBaseResponse
import co.orange.data.dto.request.IamportTokenRequestDto
import co.orange.data.dto.response.IamportCertificationDto
import co.orange.data.dto.response.IamportTokenDto
import co.orange.data.service.IamportService
import javax.inject.Inject

data class IamportDataSourceImpl
    @Inject
    constructor(
        private val iamportService: IamportService,
    ) : IamportDataSource {
        override suspend fun postToGetIamportToken(request: IamportTokenRequestDto): IamportBaseResponse<IamportTokenDto?> =
            iamportService.postToGetIamportToken(request)

        override suspend fun getIamportCertificationData(
            authorization: String,
            impUid: String,
        ): IamportBaseResponse<IamportCertificationDto?> = iamportService.getIamportCertificationData(authorization, impUid)
    }
