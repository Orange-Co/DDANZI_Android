package co.orange.data.dataSource

import co.orange.data.dto.BaseResponse
import co.orange.data.dto.request.SellCheckRequestDto
import co.orange.data.dto.response.SellCheckedProductDto
import co.orange.data.dto.response.SellProductDto
import co.orange.data.dto.response.SignedUrlDto

interface SellDataSource {
    suspend fun getSignedUrl(fileName: String): BaseResponse<SignedUrlDto>

    suspend fun postToCheckProduct(request: SellCheckRequestDto): BaseResponse<SellCheckedProductDto>

    suspend fun getProductToSell(productId: String): BaseResponse<SellProductDto>
}
