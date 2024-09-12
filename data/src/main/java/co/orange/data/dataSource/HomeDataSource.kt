package co.orange.data.dataSource

import co.orange.data.dto.BaseResponse
import co.orange.data.dto.response.AlarmListDto
import co.orange.data.dto.response.HomeDto
import co.orange.data.dto.response.SearchResultDto

interface HomeDataSource {
    suspend fun getHomeData(page: Int): BaseResponse<HomeDto>

    suspend fun getSearchResult(keyword: String): BaseResponse<SearchResultDto>

    suspend fun getAlarmList(): BaseResponse<AlarmListDto>

    suspend fun patchToReadAlarm(alarmId: Long): BaseResponse<Boolean>
}
