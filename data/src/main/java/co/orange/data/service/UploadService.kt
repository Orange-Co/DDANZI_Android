package co.orange.data.service

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Url

interface UploadService {
    @PUT
    suspend fun putImageToCloud(
        @Url signedUrl: String,
        @Body image: RequestBody,
    )
}
