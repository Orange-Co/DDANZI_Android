package co.orange.domain.repository

import co.orange.domain.entity.request.TokenRequestModel
import co.orange.domain.entity.response.AuthTokenModel

interface AuthRepository {
    suspend fun postReissueTokens(
        authorization: String,
        request: TokenRequestModel,
    ): Result<AuthTokenModel>
}
