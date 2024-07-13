package co.orange.data.dataSource

import co.orange.data.dto.BaseResponse
import co.orange.data.dto.response.SearchResultDto

interface SearchDataSource {
    suspend fun getSearchResult(keyword: String): BaseResponse<SearchResultDto>
}
