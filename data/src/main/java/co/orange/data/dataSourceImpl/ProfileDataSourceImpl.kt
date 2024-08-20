package co.orange.data.dataSourceImpl

import co.orange.data.dataSource.ProfileDataSource
import co.orange.data.dto.BaseResponse
import co.orange.data.dto.response.HistoryBuyDto
import co.orange.data.dto.response.HistoryInterestDto
import co.orange.data.dto.response.NicknameDto
import co.orange.data.service.ProfileService
import javax.inject.Inject

data class ProfileDataSourceImpl
    @Inject
    constructor(
        private val profileService: ProfileService,
    ) : ProfileDataSource {
        override suspend fun getNickname(): BaseResponse<NicknameDto> = profileService.getNickname()

        override suspend fun getInterestHistory(): BaseResponse<HistoryInterestDto> = profileService.getInterestHistory()

        override suspend fun getBuyHistory(): BaseResponse<HistoryBuyDto> = profileService.getBuyHistory()
    }
