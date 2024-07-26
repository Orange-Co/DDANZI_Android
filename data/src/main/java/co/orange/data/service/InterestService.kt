package co.orange.data.service

import co.orange.data.dto.BaseResponse
import co.orange.data.dto.response.InterestDto
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface InterestService {
    @POST("/api/v1/interest/{productId}")
    suspend fun postInterest(
        @Path("productId") productId: String,
    ): BaseResponse<InterestDto>

    @DELETE("/api/v1/interest/{productId}")
    suspend fun deleteInterest(
        @Path("productId") productId: String,
    ): BaseResponse<InterestDto>
}
