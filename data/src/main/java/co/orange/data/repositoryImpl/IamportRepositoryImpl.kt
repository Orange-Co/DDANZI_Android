package co.orange.data.repositoryImpl

import co.orange.data.dataSource.IamportDataSource
import co.orange.data.dto.request.IamportTokenRequestDto.Companion.toDto
import co.orange.domain.entity.request.IamportTokenRequestModel
import co.orange.domain.entity.response.IamportTokenModel
import co.orange.domain.repository.IamportRepository
import javax.inject.Inject

class IamportRepositoryImpl
    @Inject
    constructor(
        private val iamportDataSource: IamportDataSource,
    ) : IamportRepository {
        override suspend fun postToGetIamportToken(request: IamportTokenRequestModel): Result<IamportTokenModel> =
            runCatching {
                iamportDataSource.postToGetIamportToken(request.toDto()).data.toModel()
            }
    }
