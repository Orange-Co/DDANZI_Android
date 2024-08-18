package co.orange.data.repositoryImpl

import co.orange.data.dataSource.BuyDataSource
import co.orange.domain.entity.response.BuyProgressModel
import co.orange.domain.repository.BuyRepository
import javax.inject.Inject

class BuyRepositoryImpl
    @Inject
    constructor(
        private val buyDataSource: BuyDataSource,
    ) : BuyRepository {
        override suspend fun getBuyProgressData(productId: String): Result<BuyProgressModel> =
            runCatching {
                buyDataSource.getBuyProgressData(productId).data.toModel()
            }
    }
