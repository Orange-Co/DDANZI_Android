package co.orange.domain.repository

import co.orange.domain.entity.response.HomeModel
import co.orange.domain.entity.response.SearchResultModel

interface HomeRepository {
    suspend fun getHomeData(): Result<HomeModel>

    suspend fun getSearchResult(keyword: String): Result<SearchResultModel>
}
