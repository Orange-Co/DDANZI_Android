package co.orange.data.dataSource

import co.orange.data.dto.BaseResponse
import co.orange.data.dto.request.TokenRequestDto
import co.orange.data.dto.response.AuthTokenDto

interface AuthDataSource {
    suspend fun postReissueTokens(
        authorization: String,
        request: TokenRequestDto,
    ): BaseResponse<AuthTokenDto>
}
