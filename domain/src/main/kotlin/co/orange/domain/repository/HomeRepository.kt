package co.orange.domain.repository

import co.orange.domain.entity.response.AlarmListModel
import co.orange.domain.entity.response.HomeModel
import co.orange.domain.entity.response.SearchResultModel

interface HomeRepository {
    suspend fun getHomeData(page: Int): Result<HomeModel>

    suspend fun getSearchResult(keyword: String): Result<SearchResultModel>

    suspend fun getAlarmList(): Result<AlarmListModel>

    suspend fun patchToReadAlarm(alarmId: Long): Result<Boolean>
}
