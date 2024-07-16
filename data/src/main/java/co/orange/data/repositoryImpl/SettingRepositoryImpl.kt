package co.orange.data.repositoryImpl

import co.orange.data.dataSource.SettingDataSource
import co.orange.domain.entity.response.SettingInfoModel
import co.orange.domain.repository.SettingRepository
import javax.inject.Inject

class SettingRepositoryImpl
    @Inject
    constructor(
        private val settingDataSource: SettingDataSource,
    ) : SettingRepository {
        override suspend fun getSettingInfo(): Result<SettingInfoModel> =
            runCatching {
                settingDataSource.getSettingInfo().data.toModel()
            }
    }
