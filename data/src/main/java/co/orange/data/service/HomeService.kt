package co.orange.data.service

import co.orange.data.dto.BaseResponse
import co.orange.data.dto.response.AlarmListDto
import co.orange.data.dto.response.HomeDto
import co.orange.data.dto.response.SearchResultDto
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeService {
    @GET("/api/v1/home")
    suspend fun getHomeData(
        @Query("page") page: Int,
    ): BaseResponse<HomeDto>

    @GET("/api/v1/search")
    suspend fun getSearchResult(
        @Query("keyword") keyword: String,
    ): BaseResponse<SearchResultDto>

    @GET("/api/v1/alarm")
    suspend fun getAlarmList(): BaseResponse<AlarmListDto>

    @PATCH("/api/v1/alarm/{id}")
    suspend fun patchToReadAlarm(
        @Path("id") alarmId: Long,
    ): BaseResponse<Boolean>
}
