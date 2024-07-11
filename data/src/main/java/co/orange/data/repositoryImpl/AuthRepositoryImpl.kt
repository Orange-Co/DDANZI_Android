package co.orange.data.repositoryImpl

import co.orange.data.dataSource.AuthDataSource
import co.orange.data.dto.request.TokenRequestDto.Companion.toDto
import co.orange.domain.entity.request.TokenRequestModel
import co.orange.domain.entity.response.AuthTokenModel
import co.orange.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl
    @Inject
    constructor(
        private val authDataSource: AuthDataSource,
    ) : AuthRepository {
        override suspend fun postReissueTokens(
            authorization: String,
            request: TokenRequestModel,
        ): Result<AuthTokenModel> =
            runCatching {
                authDataSource.postReissueTokens(
                    authorization,
                    request.toDto(),
                ).data.toModel()
            }
    }
