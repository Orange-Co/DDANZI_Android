package co.orange.data.service

import co.orange.data.dto.BaseResponse
import co.orange.data.dto.request.ReissueRequestDto
import co.orange.data.dto.response.ReissueTokenDto
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    // TODO: 서버 나오면 수정
    @POST("api/users/reissue")
    suspend fun postReissueTokens(
        @Header("Authorization") authorization: String,
        @Body request: ReissueRequestDto,
    ): BaseResponse<ReissueTokenDto>
}
