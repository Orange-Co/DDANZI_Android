package co.orange.data.service

import co.orange.data.dto.BaseResponse
import co.orange.data.dto.request.AuthRequestDto
import co.orange.data.dto.request.ReissueRequestDto
import co.orange.data.dto.response.AuthTokenDto
import co.orange.data.dto.response.ReissueTokenDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    // TODO
    @POST("auth/v1/reissue")
    suspend fun postReissueTokens(
        @Body request: ReissueRequestDto,
    ): BaseResponse<ReissueTokenDto>

    @POST("/api/v1/auth/signin")
    suspend fun postOauthDataToGetToken(
        @Body request: AuthRequestDto,
    ): BaseResponse<AuthTokenDto>
}
