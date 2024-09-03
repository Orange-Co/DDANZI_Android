package co.orange.data.dataSource

import co.orange.data.dto.BaseResponse
import co.orange.data.dto.request.SellCheckRequestDto
import co.orange.data.dto.request.SellRegisterRequestDto
import co.orange.data.dto.response.OrderConfirmDto
import co.orange.data.dto.response.SellBuyerInfoDto
import co.orange.data.dto.response.SellCheckedProductDto
import co.orange.data.dto.response.SellInfoDto
import co.orange.data.dto.response.SellProductDto
import co.orange.data.dto.response.SellRegisteredDto
import co.orange.data.dto.response.SignedUrlDto

interface SellDataSource {
    suspend fun getSignedUrl(fileName: String): BaseResponse<SignedUrlDto>

    suspend fun postToCheckProduct(request: SellCheckRequestDto): BaseResponse<SellCheckedProductDto>

    suspend fun getProductToSell(productId: String): BaseResponse<SellProductDto>

    suspend fun postToRegisterProduct(request: SellRegisterRequestDto): BaseResponse<SellRegisteredDto>

    suspend fun getItemDetailInfo(itemId: String): BaseResponse<SellInfoDto>

    suspend fun getBuyerInfo(orderId: String): BaseResponse<SellBuyerInfoDto>

    suspend fun patchOrderConfirm(orderId: String): BaseResponse<OrderConfirmDto>
}
