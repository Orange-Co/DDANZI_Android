package co.orange.data.repositoryImpl

import co.orange.data.dataSource.SellDataSource
import co.orange.domain.entity.response.SignedUrlModel
import co.orange.domain.repository.SellRepository
import javax.inject.Inject

class SellRepositoryImpl
    @Inject
    constructor(
        private val sellDataSource: SellDataSource,
    ) : SellRepository {
        override suspend fun getSignedUrl(fileName: String): Result<SignedUrlModel> =
            runCatching {
                sellDataSource.getSignedUrl(fileName).data.toModel()
            }
    }
