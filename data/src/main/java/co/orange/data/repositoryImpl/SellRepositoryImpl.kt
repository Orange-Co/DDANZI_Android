package co.orange.data.repositoryImpl

import co.orange.data.dataSource.SellDataSource
import co.orange.data.dto.request.SellCheckRequestDto.Companion.toDto
import co.orange.data.dto.request.SellRegisterRequestDto.Companion.toDto
import co.orange.domain.entity.request.SellCheckRequestModel
import co.orange.domain.entity.request.SellRegisterRequestModel
import co.orange.domain.entity.response.OrderConfirmModel
import co.orange.domain.entity.response.SellBuyerInfoModel
import co.orange.domain.entity.response.SellCheckedProductModel
import co.orange.domain.entity.response.SellInfoModel
import co.orange.domain.entity.response.SellProductModel
import co.orange.domain.entity.response.SellRegisteredModel
import co.orange.domain.entity.response.SignedUrlModel
import co.orange.domain.repository.SellRepository
import javax.inject.Inject

class SellRepositoryImpl
    @Inject
    constructor(
        private val sellDataSource: SellDataSource,
    ) : SellRepository {
        override suspend fun getSignedUrl(fileName: String): Result<SignedUrlModel> =
            runCatching {
                sellDataSource.getSignedUrl(fileName).data.toModel()
            }

        override suspend fun postToCheckProduct(request: SellCheckRequestModel): Result<SellCheckedProductModel> =
            runCatching {
                sellDataSource.postToCheckProduct(request.toDto()).data.toModel()
            }

        override suspend fun getProductToSell(productId: String): Result<SellProductModel> =
            runCatching {
                sellDataSource.getProductToSell(productId).data.toModel()
            }

        override suspend fun postToRegisterProduct(request: SellRegisterRequestModel): Result<SellRegisteredModel> =
            runCatching {
                sellDataSource.postToRegisterProduct(request.toDto()).data.toModel()
            }

        override suspend fun getItemDetailInfo(itemId: String): Result<SellInfoModel> =
            runCatching {
                sellDataSource.getItemDetailInfo(itemId).data.toModel()
            }

        override suspend fun getBuyerInfo(orderId: String): Result<SellBuyerInfoModel> =
            runCatching {
                sellDataSource.getBuyerInfo(orderId).data.toModel()
            }

        override suspend fun patchOrderConfirm(orderId: String): Result<OrderConfirmModel> =
            runCatching {
                sellDataSource.patchOrderConfirm(orderId).data.toModel()
            }

        override suspend fun deleteSellingItem(itemId: String): Result<Boolean> =
            runCatching {
                sellDataSource.deleteSellingItem(itemId).data
            }
    }
