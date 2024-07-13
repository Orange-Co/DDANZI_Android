package co.orange.domain.repository

import co.orange.domain.entity.response.SearchResultModel

interface SearchRepository {
    suspend fun getSearchResult(keyword: String): Result<SearchResultModel>
}
