package co.orange.domain.repository

import co.orange.domain.entity.response.HistoryBuyModel
import co.orange.domain.entity.response.HistoryInterestModel
import co.orange.domain.entity.response.HistorySellModel
import co.orange.domain.entity.response.NicknameModel

interface ProfileRepository {
    suspend fun getNickname(): Result<NicknameModel>

    suspend fun getInterestHistory(): Result<HistoryInterestModel>

    suspend fun getBuyHistory(): Result<HistoryBuyModel>

    suspend fun getSellHistory(): Result<HistorySellModel>
}
