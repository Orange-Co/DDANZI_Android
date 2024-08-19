package co.orange.domain.repository

import co.orange.domain.entity.request.OrderRequestModel
import co.orange.domain.entity.request.PayEndRequestModel
import co.orange.domain.entity.request.PayStartRequestModel
import co.orange.domain.entity.response.BuyProgressModel
import co.orange.domain.entity.response.OrderIdModel
import co.orange.domain.entity.response.OrderInfoModel
import co.orange.domain.entity.response.PayEndModel
import co.orange.domain.entity.response.PayStartModel

interface BuyRepository {
    suspend fun getBuyProgressData(productId: String): Result<BuyProgressModel>

    suspend fun postPaymentStart(request: PayStartRequestModel): Result<PayStartModel>

    suspend fun patchPaymentEnd(request: PayEndRequestModel): Result<PayEndModel>

    suspend fun postToRequestOrder(request: OrderRequestModel): Result<OrderIdModel>

    suspend fun getOrderInfo(orderId: String): Result<OrderInfoModel>
}
