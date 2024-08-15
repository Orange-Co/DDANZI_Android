package co.orange.data.dataSource

import co.orange.data.dto.BaseResponse
import co.orange.data.dto.request.AuthRequestDto
import co.orange.data.dto.request.ReissueRequestDto
import co.orange.data.dto.request.SignUpRequestDto
import co.orange.data.dto.response.AuthTokenDto
import co.orange.data.dto.response.ReissueTokenDto
import co.orange.data.dto.response.SignUpDto

interface AuthDataSource {
    suspend fun postReissueTokens(request: ReissueRequestDto): BaseResponse<ReissueTokenDto>

    suspend fun postOauthDataToGetToken(request: AuthRequestDto): BaseResponse<AuthTokenDto>

    suspend fun postToSignUp(
        accesstoken: String,
        request: SignUpRequestDto,
    ): BaseResponse<SignUpDto>
}
