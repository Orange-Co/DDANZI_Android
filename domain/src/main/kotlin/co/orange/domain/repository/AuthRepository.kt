package co.orange.domain.repository

import co.orange.domain.entity.request.AuthRequestModel
import co.orange.domain.entity.request.ReissueRequestModel
import co.orange.domain.entity.request.SignUpRequestModel
import co.orange.domain.entity.response.AuthTokenModel
import co.orange.domain.entity.response.ReissueTokenModel
import co.orange.domain.entity.response.SignUpModel

interface AuthRepository {
    suspend fun postReissueTokens(request: ReissueRequestModel): Result<ReissueTokenModel>

    suspend fun postOauthDataToGetToken(request: AuthRequestModel): Result<AuthTokenModel>

    suspend fun postToSignUp(
        accesstoken: String,
        request: SignUpRequestModel,
    ): Result<SignUpModel>
}
