package co.orange.data.dataSourceImpl

import co.orange.data.dataSource.BuyDataSource
import co.orange.data.dto.BaseResponse
import co.orange.data.dto.request.OrderRequestDto
import co.orange.data.dto.request.PayEndRequestDto
import co.orange.data.dto.request.PayStartRequestDto
import co.orange.data.dto.response.BuyProgressDto
import co.orange.data.dto.response.OrderConfirmDto
import co.orange.data.dto.response.OrderIdDto
import co.orange.data.dto.response.OrderInfoDto
import co.orange.data.dto.response.PayEndDto
import co.orange.data.dto.response.PayStartDto
import co.orange.data.service.BuyService
import javax.inject.Inject

data class BuyDataSourceImpl
    @Inject
    constructor(
        private val buyService: BuyService,
    ) : BuyDataSource {
        override suspend fun getBuyProgressData(productId: String): BaseResponse<BuyProgressDto> = buyService.getBuyProgressData(productId)

        override suspend fun postPaymentStart(request: PayStartRequestDto): BaseResponse<PayStartDto> = buyService.postPaymentStart(request)

        override suspend fun patchPaymentEnd(request: PayEndRequestDto): BaseResponse<PayEndDto> = buyService.patchPaymentEnd(request)

        override suspend fun postToRequestOrder(request: OrderRequestDto): BaseResponse<OrderIdDto> = buyService.postToRequestOrder(request)

        override suspend fun getOrderInfo(orderId: String): BaseResponse<OrderInfoDto> = buyService.getOrderInfo(orderId)

        override suspend fun patchOrderConfirm(orderId: String): BaseResponse<OrderConfirmDto> = buyService.patchOrderConfirm(orderId)
    }
