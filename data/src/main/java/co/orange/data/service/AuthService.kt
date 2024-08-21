package co.orange.data.service

import co.orange.data.dto.BaseResponse
import co.orange.data.dto.request.AuthRequestDto
import co.orange.data.dto.request.ReissueRequestDto
import co.orange.data.dto.request.SignUpRequestDto
import co.orange.data.dto.response.AuthTokenDto
import co.orange.data.dto.response.ReissueTokenDto
import co.orange.data.dto.response.SignUpDto
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("/api/v1/auth/refreshtoken")
    suspend fun postReissueTokens(
        @Body request: ReissueRequestDto,
    ): BaseResponse<ReissueTokenDto>

    @POST("/api/v1/auth/signin")
    suspend fun postOauthDataToGetToken(
        @Body request: AuthRequestDto,
    ): BaseResponse<AuthTokenDto>

    @POST("/api/v1/auth/verification")
    suspend fun postToSignUp(
        @Header("Authorization") accesstoken: String,
        @Body request: SignUpRequestDto,
    ): BaseResponse<SignUpDto>
}
