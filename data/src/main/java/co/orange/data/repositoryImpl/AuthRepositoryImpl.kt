package co.orange.data.repositoryImpl

import co.orange.data.dataSource.AuthDataSource
import co.orange.data.dto.request.ReissueRequestDto.Companion.toDto
import co.orange.domain.entity.request.ReissueRequestModel
import co.orange.domain.entity.response.ReissueTokenModel
import co.orange.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl
    @Inject
    constructor(
        private val authDataSource: AuthDataSource,
    ) : AuthRepository {
        override suspend fun postReissueTokens(
            authorization: String,
            request: ReissueRequestModel,
        ): Result<ReissueTokenModel> =
            runCatching {
                authDataSource.postReissueTokens(
                    authorization,
                    request.toDto(),
                ).data.toModel()
            }
    }
