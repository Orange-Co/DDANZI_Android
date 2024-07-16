package co.orange.data.dataSource

import co.orange.data.dto.BaseResponse
import co.orange.data.dto.response.SettingInfoDto

interface SettingDataSource {
    suspend fun getSettingInfo(): BaseResponse<SettingInfoDto>
}
