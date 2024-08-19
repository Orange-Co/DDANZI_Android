package co.orange.domain.repository

import co.orange.domain.entity.request.PayStartRequestModel
import co.orange.domain.entity.response.BuyProgressModel
import co.orange.domain.entity.response.PayStartModel

interface BuyRepository {
    suspend fun getBuyProgressData(productId: String): Result<BuyProgressModel>

    suspend fun postPaymentStart(request: PayStartRequestModel): Result<PayStartModel>
}
