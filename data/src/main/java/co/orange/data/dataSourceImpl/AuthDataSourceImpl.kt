package co.orange.data.dataSourceImpl

import co.orange.data.dataSource.AuthDataSource
import co.orange.data.dto.BaseResponse
import co.orange.data.dto.request.AuthTokenRequestDto
import co.orange.data.dto.response.AuthTokenDto
import co.orange.data.service.AuthService
import javax.inject.Inject

data class AuthDataSourceImpl
    @Inject
    constructor(
        private val authService: AuthService,
    ) : AuthDataSource {
        override suspend fun postReissueTokens(
            authorization: String,
            request: AuthTokenRequestDto,
        ): BaseResponse<AuthTokenDto> = authService.postReissueTokens(authorization, request)
    }
