package co.orange.data.dataSource

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

interface BuyDataSource {
    suspend fun getBuyProgressData(productId: String): BaseResponse<BuyProgressDto>

    suspend fun postPaymentStart(request: PayStartRequestDto): BaseResponse<PayStartDto>

    suspend fun patchPaymentEnd(request: PayEndRequestDto): BaseResponse<PayEndDto>

    suspend fun postToRequestOrder(request: OrderRequestDto): BaseResponse<OrderIdDto>

    suspend fun getOrderInfo(orderId: String): BaseResponse<OrderInfoDto>

    suspend fun patchOrderConfirm(orderId: String): BaseResponse<OrderConfirmDto>
}
