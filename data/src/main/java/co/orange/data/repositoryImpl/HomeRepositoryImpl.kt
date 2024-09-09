package co.orange.data.repositoryImpl

import co.orange.data.dataSource.HomeDataSource
import co.orange.domain.entity.response.AlarmListModel
import co.orange.domain.entity.response.HomeModel
import co.orange.domain.entity.response.SearchResultModel
import co.orange.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl
    @Inject
    constructor(
        private val homeDataSource: HomeDataSource,
    ) : HomeRepository {
        override suspend fun getHomeData(): Result<HomeModel> = runCatching { homeDataSource.getHomeData().data.toModel() }

        override suspend fun getSearchResult(keyword: String): Result<SearchResultModel> =
            runCatching {
                homeDataSource.getSearchResult(keyword).data.toModel()
            }

        override suspend fun getAlarmList(): Result<AlarmListModel> =
            runCatching {
                homeDataSource.getAlarmList().data.toModel()
            }

        override suspend fun patchToReadAlarm(alarmId: Long): Result<Boolean> =
            runCatching {
                homeDataSource.patchToReadAlarm(alarmId).data
            }
    }
