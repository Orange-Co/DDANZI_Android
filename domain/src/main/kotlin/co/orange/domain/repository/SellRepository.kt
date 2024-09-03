package co.orange.domain.repository

import co.orange.domain.entity.request.SellCheckRequestModel
import co.orange.domain.entity.request.SellRegisterRequestModel
import co.orange.domain.entity.response.SellBuyerInfoModel
import co.orange.domain.entity.response.SellCheckedProductModel
import co.orange.domain.entity.response.SellInfoModel
import co.orange.domain.entity.response.SellProductModel
import co.orange.domain.entity.response.SellRegisteredModel
import co.orange.domain.entity.response.SignedUrlModel

interface SellRepository {
    suspend fun getSignedUrl(fileName: String): Result<SignedUrlModel>

    suspend fun postToCheckProduct(request: SellCheckRequestModel): Result<SellCheckedProductModel>

    suspend fun getProductToSell(productId: String): Result<SellProductModel>

    suspend fun postToRegisterProduct(request: SellRegisterRequestModel): Result<SellRegisteredModel>

    suspend fun getItemDetailInfo(itemId: String): Result<SellInfoModel>

    suspend fun getBuyerInfo(orderId: String): Result<SellBuyerInfoModel>
}
