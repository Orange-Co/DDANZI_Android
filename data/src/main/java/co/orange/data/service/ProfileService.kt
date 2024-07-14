package co.orange.data.service

import co.orange.data.dto.BaseResponse
import co.orange.data.dto.response.ProfileNicknameDto
import retrofit2.http.GET

interface ProfileService {
    @GET("/api/v1/mypage")
    suspend fun geNickname(): BaseResponse<ProfileNicknameDto>
}
