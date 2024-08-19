package co.orange.data.repositoryImpl

import co.orange.data.dataSource.BuyDataSource
import co.orange.data.dto.request.OrderRequestDto.Companion.toDto
import co.orange.data.dto.request.PayEndRequestDto.Companion.toDto
import co.orange.data.dto.request.PayStartRequestDto.Companion.toDto
import co.orange.domain.entity.request.OrderRequestModel
import co.orange.domain.entity.request.PayEndRequestModel
import co.orange.domain.entity.request.PayStartRequestModel
import co.orange.domain.entity.response.BuyProgressModel
import co.orange.domain.entity.response.OrderIdModel
import co.orange.domain.entity.response.OrderInfoModel
import co.orange.domain.entity.response.PayEndModel
import co.orange.domain.entity.response.PayStartModel
import co.orange.domain.repository.BuyRepository
import javax.inject.Inject

class BuyRepositoryImpl
    @Inject
    constructor(
        private val buyDataSource: BuyDataSource,
    ) : BuyRepository {
        override suspend fun getBuyProgressData(productId: String): Result<BuyProgressModel> =
            runCatching {
                buyDataSource.getBuyProgressData(productId).data.toModel()
            }

        override suspend fun postPaymentStart(request: PayStartRequestModel): Result<PayStartModel> =
            runCatching {
                buyDataSource.postPaymentStart(request.toDto()).data.toModel()
            }

        override suspend fun patchPaymentEnd(request: PayEndRequestModel): Result<PayEndModel> =
            runCatching {
                buyDataSource.patchPaymentEnd(request.toDto()).data.toModel()
            }

        override suspend fun postToRequestOrder(request: OrderRequestModel): Result<OrderIdModel> =
            runCatching {
                buyDataSource.postToRequestOrder(request.toDto()).data.toModel()
            }

        override suspend fun getOrderInfo(orderId: String): Result<OrderInfoModel> =
            runCatching {
                buyDataSource.getOrderInfo(orderId).data.toModel()
            }
    }
