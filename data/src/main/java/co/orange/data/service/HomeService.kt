package co.orange.data.service

import co.orange.data.dto.BaseResponse
import co.orange.data.dto.response.HomeDto
import co.orange.data.dto.response.SearchResultDto
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeService {
    @GET("/api/v1/home")
    suspend fun getHomeData(): BaseResponse<HomeDto>

    @GET("/api/v1/search")
    suspend fun getSearchResult(
        @Query("keyword") keyword: String,
    ): BaseResponse<SearchResultDto>
}
