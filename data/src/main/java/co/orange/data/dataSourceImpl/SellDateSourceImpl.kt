package co.orange.data.dataSourceImpl

import co.orange.data.dataSource.SellDataSource
import co.orange.data.dto.BaseResponse
import co.orange.data.dto.request.SellCheckRequestDto
import co.orange.data.dto.response.SellCheckedProductDto
import co.orange.data.dto.response.SellProductDto
import co.orange.data.dto.response.SignedUrlDto
import co.orange.data.service.SellService
import javax.inject.Inject

data class SellDateSourceImpl
    @Inject
    constructor(
        private val sellService: SellService,
    ) : SellDataSource {
        override suspend fun getSignedUrl(fileName: String): BaseResponse<SignedUrlDto> = sellService.getSignedUrl(fileName)

        override suspend fun postToCheckProduct(request: SellCheckRequestDto): BaseResponse<SellCheckedProductDto> =
            sellService.postToCheckProduct(request)

        override suspend fun getProductToSell(productId: String): BaseResponse<SellProductDto> = sellService.getProductToSell(productId)
    }
