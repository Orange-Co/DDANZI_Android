package co.orange.data.service

import co.orange.data.dto.BaseResponse
import co.orange.data.dto.response.SignedUrlDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SellService {
    @GET("/api/v1/item/signed-url")
    suspend fun getSignedUrl(
        @Query("fileName") fileName: String,
    ): BaseResponse<SignedUrlDto>
}
