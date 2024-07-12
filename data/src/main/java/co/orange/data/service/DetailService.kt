package co.orange.data.service

import co.orange.data.dto.BaseResponse
import co.orange.data.dto.response.ProductDetailDto
import retrofit2.http.Body
import retrofit2.http.GET

interface DetailService {
    @GET("/api/v1/home/product")
    suspend fun getProductDetail(
        @Body id: String,
    ): BaseResponse<ProductDetailDto>
}
