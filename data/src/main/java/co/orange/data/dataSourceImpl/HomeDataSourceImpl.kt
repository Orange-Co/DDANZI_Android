package co.orange.data.dataSourceImpl

import co.orange.data.dataSource.HomeDataSource
import co.orange.data.dto.BaseResponse
import co.orange.data.dto.response.HomeDto
import co.orange.data.dto.response.SearchResultDto
import co.orange.data.service.HomeService
import javax.inject.Inject

data class HomeDataSourceImpl
    @Inject
    constructor(
        private val homeService: HomeService,
    ) : HomeDataSource {
        override suspend fun getHomeData(): BaseResponse<HomeDto> = homeService.getHomeData()

        override suspend fun getSearchResult(keyword: String): BaseResponse<SearchResultDto> = homeService.getSearchResult(keyword)
    }
