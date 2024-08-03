package co.orange.domain.repository

import co.orange.domain.entity.request.AuthTokenRequestModel
import co.orange.domain.entity.response.AuthTokenModel

interface AuthRepository {
    suspend fun postReissueTokens(
        authorization: String,
        request: AuthTokenRequestModel,
    ): Result<AuthTokenModel>
}
