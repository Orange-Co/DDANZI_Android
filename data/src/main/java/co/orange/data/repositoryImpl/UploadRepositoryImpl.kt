package co.orange.data.repositoryImpl

import android.content.Context
import android.net.Uri
import co.orange.data.service.UploadService
import co.orange.data.util.ContentUriRequestBody
import co.orange.domain.repository.UploadRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UploadRepositoryImpl
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
        private val uploadService: UploadService,
    ) : UploadRepository {
        override suspend fun putImageToCloud(
            signedUrl: String,
            image: String,
        ): Result<Unit> =
            runCatching {
                uploadService.putImageToCloud(
                    signedUrl,
                    ContentUriRequestBody(context, Uri.parse(image)),
                )
            }
    }
