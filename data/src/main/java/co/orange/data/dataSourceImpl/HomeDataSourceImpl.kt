package co.orange.data.dataSourceImpl

import co.orange.data.dataSource.HomeDataSource
import co.orange.data.dto.BaseResponse
import co.orange.data.dto.response.AlarmListDto
import co.orange.data.dto.response.HomeDto
import co.orange.data.dto.response.SearchResultDto
import co.orange.data.service.HomeService
import javax.inject.Inject

data class HomeDataSourceImpl
    @Inject
    constructor(
        private val homeService: HomeService,
    ) : HomeDataSource {
        override suspend fun getHomeData(page: Int): BaseResponse<HomeDto> = homeService.getHomeData(page)

        override suspend fun getSearchResult(keyword: String): BaseResponse<SearchResultDto> = homeService.getSearchResult(keyword)

        override suspend fun getAlarmList(): BaseResponse<AlarmListDto> = homeService.getAlarmList()

        override suspend fun patchToReadAlarm(alarmId: Long): BaseResponse<Boolean> = homeService.patchToReadAlarm(alarmId)
    }
