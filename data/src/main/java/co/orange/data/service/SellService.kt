package co.orange.data.service

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
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface SellService {
    @GET("/api/v1/item/signed-url")
    suspend fun getSignedUrl(
        @Query("fileName") fileName: String,
    ): BaseResponse<SignedUrlDto>

    @POST("/api/v1/item/check")
    suspend fun postToCheckProduct(
        @Body request: SellCheckRequestDto,
    ): BaseResponse<SellCheckedProductDto>

    @GET("/api/v1/item/product/{id}")
    suspend fun getProductToSell(
        @Path("id") productId: String,
    ): BaseResponse<SellProductDto>

    @POST("/api/v1/item")
    suspend fun postToRegisterProduct(
        @Body request: SellRegisterRequestDto,
    ): BaseResponse<SellRegisteredDto>

    @GET("/api/v1/item/{id}")
    suspend fun getItemDetailInfo(
        @Path("id") itemId: String,
    ): BaseResponse<SellInfoDto>

    @GET("/api/v1/item/order/{id}")
    suspend fun getBuyerInfo(
        @Path("id") orderId: String,
    ): BaseResponse<SellBuyerInfoDto>

    @PATCH("/api/v1/order/{id}/sale")
    suspend fun patchOrderConfirm(
        @Path("id") orderId: String,
    ): BaseResponse<OrderConfirmDto>

    @DELETE("/api/v1/item/{id}")
    suspend fun deleteSellingItem(
        @Path("id") itemId: String,
    ): BaseResponse<Boolean>
}
