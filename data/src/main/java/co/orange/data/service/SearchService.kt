package co.orange.data.service

import co.orange.data.dto.BaseResponse
import co.orange.data.dto.response.SearchResultDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("/api/v1/search")
    suspend fun getSearchResult(
        @Query("keyword") keyword: String,
    ): BaseResponse<SearchResultDto>
}
