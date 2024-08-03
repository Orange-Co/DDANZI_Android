package co.orange.domain.repository

import co.orange.domain.entity.response.IamportTokenModel

interface IamportRepository {
    suspend fun postToGetIamportToken(): Result<IamportTokenModel>
}
