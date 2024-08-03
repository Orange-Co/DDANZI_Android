package co.orange.data.service

import co.orange.data.dto.IamportBaseResponse
import co.orange.data.dto.request.IamportTokenRequestDto
import co.orange.data.dto.response.IamportCertificationDto
import co.orange.data.dto.response.IamportTokenDto
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface IamportService {
    @POST("/users/getToken")
    suspend fun postToGetIamportToken(
        @Body request: IamportTokenRequestDto,
    ): IamportBaseResponse<IamportTokenDto?>

    @POST("/certifications/{imp_uid}")
    suspend fun postToGetCertificationData(
        @Header("Authorization") authorization: String,
        @Path("imp_uid") impUid: String,
    ): IamportBaseResponse<IamportCertificationDto?>
}
