package co.orange.data.service

import co.orange.data.dto.BaseResponse
import co.orange.data.dto.request.PayEndRequestDto
import co.orange.data.dto.request.PayStartRequestDto
import co.orange.data.dto.response.BuyProgressDto
import co.orange.data.dto.response.PayEndDto
import co.orange.data.dto.response.PayStartDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface BuyService {
    @GET("/api/v1/order/product/{id}")
    suspend fun getBuyProgressData(
        @Path("id") productId: String,
    ): BaseResponse<BuyProgressDto>

    @POST("/api/v1/payment/start")
    suspend fun postPaymentStart(
        @Body request: PayStartRequestDto,
    ): BaseResponse<PayStartDto>

    @PATCH("/api/v1/payment/end")
    suspend fun patchPaymentEnd(
        @Body request: PayEndRequestDto,
    ): BaseResponse<PayEndDto>
}
