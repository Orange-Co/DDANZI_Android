package co.orange.data.dataSourceImpl

import co.orange.data.dataSource.AuthDataSource
import co.orange.data.dto.BaseResponse
import co.orange.data.dto.request.AuthRequestDto
import co.orange.data.dto.request.ReissueRequestDto
import co.orange.data.dto.response.AuthTokenDto
import co.orange.data.dto.response.ReissueTokenDto
import co.orange.data.service.AuthService
import javax.inject.Inject

data class AuthDataSourceImpl
    @Inject
    constructor(
        private val authService: AuthService,
    ) : AuthDataSource {
        override suspend fun postReissueTokens(request: ReissueRequestDto): BaseResponse<ReissueTokenDto> =
            authService.postReissueTokens(request)

        override suspend fun postOauthDataToGetToken(request: AuthRequestDto): BaseResponse<AuthTokenDto> =
            authService.postOauthDataToGetToken(request)
    }
