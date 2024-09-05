package co.orange.data.dataSource

import co.orange.data.dto.BaseResponse
import co.orange.data.dto.response.HistoryBuyDto
import co.orange.data.dto.response.HistoryInterestDto
import co.orange.data.dto.response.HistorySellDto
import co.orange.data.dto.response.NicknameDto

interface ProfileDataSource {
    suspend fun getNickname(): BaseResponse<NicknameDto>

    suspend fun getInterestHistory(): BaseResponse<HistoryInterestDto>

    suspend fun getBuyHistory(): BaseResponse<HistoryBuyDto>

    suspend fun getSellHistory(): BaseResponse<HistorySellDto>
}
