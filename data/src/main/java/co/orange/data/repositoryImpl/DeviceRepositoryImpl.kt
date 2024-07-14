package co.orange.data.repositoryImpl

import co.orange.data.dataSource.DeviceDataSource
import co.orange.domain.entity.response.ProductDetailModel
import co.orange.domain.repository.DeviceRepository
import javax.inject.Inject

class DeviceRepositoryImpl
    @Inject
    constructor(
        private val deviceDataSource: DeviceDataSource,
    ) : DeviceRepository {
        override suspend fun getProductDetail(id: String): Result<ProductDetailModel> =
            runCatching {
                deviceDataSource.getProductDetail(id).data.toModel()
            }
    }
