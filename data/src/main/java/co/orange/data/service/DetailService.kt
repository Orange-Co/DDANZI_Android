package co.orange.data.service

import co.orange.data.dto.BaseResponse
import co.orange.data.dto.response.ProductDetailDto
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailService {
    @GET("/api/v1/home/product/{id}")
    suspend fun getProductDetail(
        @Path("id") id: String,
    ): BaseResponse<ProductDetailDto>
}
