package co.orange.data.repositoryImpl

import co.orange.data.dataSource.DetailDataSource
import co.orange.domain.entity.response.ProductDetailModel
import co.orange.domain.repository.DetailRepository
import javax.inject.Inject

class DetailRepositoryImpl
    @Inject
    constructor(
        private val detailDataSource: DetailDataSource,
    ) : DetailRepository {
        override suspend fun getProductDetail(id: String): Result<ProductDetailModel> =
            runCatching {
                detailDataSource.getHomeData(id).data.toModel()
            }
    }
