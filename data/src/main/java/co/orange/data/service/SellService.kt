package co.orange.data.service

import co.orange.data.dto.BaseResponse
import co.orange.data.dto.request.SellCheckRequestDto
import co.orange.data.dto.request.SellRegisterRequestDto
import co.orange.data.dto.response.SellCheckedProductDto
import co.orange.data.dto.response.SellProductDto
import co.orange.data.dto.response.SellRegisteredDto
import co.orange.data.dto.response.SignedUrlDto
import retrofit2.http.Body
import retrofit2.http.GET
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
}
