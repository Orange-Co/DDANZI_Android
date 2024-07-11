package co.orange.domain.repository

import co.orange.domain.entity.response.HomeModel

interface HomeRepository {
    suspend fun getHomeData(): Result<HomeModel>
}
