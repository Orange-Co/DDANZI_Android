package co.orange.data.dataSource

import co.orange.data.dto.BaseResponse
import co.orange.data.dto.response.HomeDto

interface HomeDataSource {
    suspend fun getHomeData(): BaseResponse<HomeDto>
}
