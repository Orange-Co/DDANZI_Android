package co.orange.data.dataSource

import co.orange.data.dto.BaseResponse
import co.orange.data.dto.request.AuthTokenRequestDto
import co.orange.data.dto.response.AuthTokenDto

interface AuthDataSource {
    suspend fun postReissueTokens(
        authorization: String,
        request: AuthTokenRequestDto,
    ): BaseResponse<AuthTokenDto>
}
