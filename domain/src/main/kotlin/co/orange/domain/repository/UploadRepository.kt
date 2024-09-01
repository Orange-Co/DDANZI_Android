package co.orange.domain.repository

interface UploadRepository {
    suspend fun putImageToCloud(
        signedUrl: String,
        image: String,
    ): Result<Unit>
}
