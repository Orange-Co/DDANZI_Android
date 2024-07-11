package co.orange.data.repositoryImpl

import co.orange.data.dataSource.HomeDataSource
import co.orange.domain.entity.response.HomeModel
import co.orange.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl
    @Inject
    constructor(
        private val homeDataSource: HomeDataSource,
    ) : HomeRepository {
        override suspend fun getHomeData(): Result<HomeModel> = runCatching { homeDataSource.getHomeData().data.toModel() }
    }
