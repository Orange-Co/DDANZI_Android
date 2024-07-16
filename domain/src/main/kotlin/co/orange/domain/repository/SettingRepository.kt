package co.orange.domain.repository

import co.orange.domain.entity.response.SettingInfoModel

interface SettingRepository {
    suspend fun getSettingInfo(): Result<SettingInfoModel>
}
