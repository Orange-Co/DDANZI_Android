package co.orange.data.repositoryImpl

import co.orange.data.dataSource.ProfileDataSource
import co.orange.domain.entity.response.HistoryBuyModel
import co.orange.domain.entity.response.HistoryInterestModel
import co.orange.domain.entity.response.HistorySellModel
import co.orange.domain.entity.response.NicknameModel
import co.orange.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl
    @Inject
    constructor(
        private val profileDataSource: ProfileDataSource,
    ) : ProfileRepository {
        override suspend fun getNickname(): Result<NicknameModel> =
            runCatching {
                profileDataSource.getNickname().data.toModel()
            }

        override suspend fun getInterestHistory(): Result<HistoryInterestModel> =
            runCatching {
                profileDataSource.getInterestHistory().data.toModel()
            }

        override suspend fun getBuyHistory(): Result<HistoryBuyModel> =
            runCatching {
                profileDataSource.getBuyHistory().data.toModel()
            }

        override suspend fun getSellHistory(): Result<HistorySellModel> =
            kotlin.runCatching {
                profileDataSource.getSellHistory().data.toModel()
            }
    }
