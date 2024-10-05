package co.orange.data.dataSourceImpl

import co.orange.data.dataSource.SellDataSource
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

        override suspend fun postToRegisterProduct(request: SellRegisterRequestDto): BaseResponse<SellRegisteredDto> =
            sellService.postToRegisterProduct(request)

        override suspend fun getItemDetailInfo(itemId: String): BaseResponse<SellInfoDto> = sellService.getItemDetailInfo(itemId)

        override suspend fun getBuyerInfo(orderId: String): BaseResponse<SellBuyerInfoDto> = sellService.getBuyerInfo(orderId)

        override suspend fun patchOrderConfirm(orderId: String): BaseResponse<OrderConfirmDto> = sellService.patchOrderConfirm(orderId)

        override suspend fun deleteSellingItem(itemId: String): BaseResponse<Boolean> = sellService.deleteSellingItem(itemId)
    }
