package co.orange.data.dataSource

import co.orange.data.dto.BaseResponse
import co.orange.data.dto.request.AuthRequestDto
import co.orange.data.dto.request.ReissueRequestDto
import co.orange.data.dto.response.AuthTokenDto
import co.orange.data.dto.response.ReissueTokenDto

interface AuthDataSource {
    suspend fun postReissueTokens(request: ReissueRequestDto): BaseResponse<ReissueTokenDto>

    suspend fun postOauthDataToGetToken(request: AuthRequestDto): BaseResponse<AuthTokenDto>
}
