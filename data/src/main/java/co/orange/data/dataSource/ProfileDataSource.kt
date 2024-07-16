package co.orange.data.dataSource

import co.orange.data.dto.BaseResponse
import co.orange.data.dto.response.ProfileInterestDto
import co.orange.data.dto.response.ProfileNicknameDto

interface ProfileDataSource {
    suspend fun getNickname(): BaseResponse<ProfileNicknameDto>

    suspend fun getInterestList(): BaseResponse<ProfileInterestDto>
}
