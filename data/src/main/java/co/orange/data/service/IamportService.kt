package co.orange.data.service

import co.orange.data.dto.IamportBaseResponse
import co.orange.data.dto.request.IamportTokenRequestDto
import co.orange.data.dto.response.IamportTokenDto
import retrofit2.http.Body
import retrofit2.http.POST

interface IamportService {
    @POST("/users/getToken")
    suspend fun postToGetIamportToken(
        @Body request: IamportTokenRequestDto,
    ): IamportBaseResponse<IamportTokenDto>
}
