package co.orange.data.dataSource

import co.orange.data.dto.BaseResponse
import co.orange.data.dto.request.ReissueRequestDto
import co.orange.data.dto.response.ReissueTokenDto

interface AuthDataSource {
    suspend fun postReissueTokens(
        authorization: String,
        request: ReissueRequestDto,
    ): BaseResponse<ReissueTokenDto>
}
