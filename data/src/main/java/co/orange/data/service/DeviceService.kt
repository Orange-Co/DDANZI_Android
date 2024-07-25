package co.orange.data.service

import co.orange.data.dto.BaseResponse
import co.orange.data.dto.response.ProductDetailDto
import co.orange.data.dto.response.SearchInfoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface DeviceService {
    @GET("/api/v1/home/product/{id}")
    suspend fun getProductDetail(
        @Path("id") id: String,
    ): BaseResponse<ProductDetailDto>

    suspend fun getSearchInfo(): BaseResponse<SearchInfoDto>
}
