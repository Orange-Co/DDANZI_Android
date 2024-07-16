package co.orange.data.dataSourceImpl

import co.orange.data.dataSource.SettingDataSource
import co.orange.data.dto.BaseResponse
import co.orange.data.dto.response.SettingInfoDto
import co.orange.data.service.SettingService
import javax.inject.Inject

data class SettingDataSourceImpl
    @Inject
    constructor(
        private val settingService: SettingService,
    ) : SettingDataSource {
        override suspend fun getSettingInfo(): BaseResponse<SettingInfoDto> = settingService.getSettingInfo()
    }
