package co.orange.data.repositoryImpl

import co.orange.data.dataSource.SearchDataSource
import co.orange.domain.entity.response.SearchResultModel
import co.orange.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl
    @Inject
    constructor(
        private val searchDataSource: SearchDataSource,
    ) : SearchRepository {
        override suspend fun getSearchResult(keyword: String): Result<SearchResultModel> =
            runCatching {
                searchDataSource.getSearchResult(keyword).data.toModel()
            }
    }
