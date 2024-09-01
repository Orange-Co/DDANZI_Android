package co.orange.data.dataSourceImpl

import co.orange.data.dataSource.SellDataSource
import co.orange.data.dto.BaseResponse
import co.orange.data.dto.response.SignedUrlDto
import co.orange.data.service.SellService
import javax.inject.Inject

data class SellDateSourceImpl
    @Inject
    constructor(
        private val sellService: SellService,
    ) : SellDataSource {
        override suspend fun getSignedUrl(fileName: String): BaseResponse<SignedUrlDto> = sellService.getSignedUrl(fileName)
    }
