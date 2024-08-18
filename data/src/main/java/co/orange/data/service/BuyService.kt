package co.orange.data.service

import co.orange.data.dto.BaseResponse
import co.orange.data.dto.response.BuyProgressDto
import retrofit2.http.GET
import retrofit2.http.Path

interface BuyService {
    @GET("/api/v1/order/product/{id}")
    suspend fun getBuyProgressData(
        @Path("id") productId: String,
    ): BaseResponse<BuyProgressDto>
}
