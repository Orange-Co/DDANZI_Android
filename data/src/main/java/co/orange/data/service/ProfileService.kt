package co.orange.data.service

import co.orange.data.dto.BaseResponse
import co.orange.data.dto.response.HistoryBuyDto
import co.orange.data.dto.response.HistoryInterestDto
import co.orange.data.dto.response.HistorySellDto
import co.orange.data.dto.response.NicknameDto
import retrofit2.http.GET

interface ProfileService {
    @GET("/api/v1/mypage")
    suspend fun getNickname(): BaseResponse<NicknameDto>

    @GET("/api/v1/mypage/interest")
    suspend fun getInterestHistory(): BaseResponse<HistoryInterestDto>

    @GET("/api/v1/mypage/order")
    suspend fun getBuyHistory(): BaseResponse<HistoryBuyDto>

    @GET("/api/v1/mypage/item")
    suspend fun getSellHistory(): BaseResponse<HistorySellDto>
}
