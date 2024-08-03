package co.orange.data.service

import co.orange.data.dto.BaseResponse
import co.orange.data.dto.request.AuthTokenRequestDto
import co.orange.data.dto.response.AuthTokenDto
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    // TODO: 서버 나오면 수정
    @POST("api/users/reissue")
    suspend fun postReissueTokens(
        @Header("Authorization") authorization: String,
        @Body request: AuthTokenRequestDto,
    ): BaseResponse<AuthTokenDto>
}
