package co.orange.data.dataSourceImpl

import co.orange.data.dataSource.SearchDataSource
import co.orange.data.dto.BaseResponse
import co.orange.data.dto.response.SearchResultDto
import co.orange.data.service.SearchService
import javax.inject.Inject

data class SearchDataSourceImpl
    @Inject
    constructor(
        private val searchService: SearchService,
    ) : SearchDataSource {
        override suspend fun getSearchResult(keyword: String): BaseResponse<SearchResultDto> = searchService.getSearchResult(keyword)
    }
