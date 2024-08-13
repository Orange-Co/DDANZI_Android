package co.orange.domain.repository

import co.orange.domain.entity.request.AuthRequestModel
import co.orange.domain.entity.request.ReissueRequestModel
import co.orange.domain.entity.response.AuthTokenModel
import co.orange.domain.entity.response.ReissueTokenModel

interface AuthRepository {
    suspend fun postReissueTokens(request: ReissueRequestModel): Result<ReissueTokenModel>

    suspend fun postOauthDataToGetToken(request: AuthRequestModel): Result<AuthTokenModel>
}
