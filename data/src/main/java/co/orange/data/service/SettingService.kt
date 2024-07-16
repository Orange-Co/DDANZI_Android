package co.orange.data.service

import co.orange.data.dto.BaseResponse
import co.orange.data.dto.response.SettingInfoDto
import retrofit2.http.GET

interface SettingService {
    @GET("/api/v1/mypage/setting")
    suspend fun getSettingInfo(): BaseResponse<SettingInfoDto>
}
