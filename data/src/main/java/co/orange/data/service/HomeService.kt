package co.orange.data.service

import co.orange.data.dto.BaseResponse
import co.orange.data.dto.response.HomeDto
import retrofit2.http.GET

interface HomeService {
    @GET("/api/v1/home")
    suspend fun getHomeData(): BaseResponse<HomeDto>
}
