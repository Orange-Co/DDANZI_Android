package co.orange.data.dataSourceImpl

import co.orange.data.dto.BaseResponse
import co.orange.data.dto.response.ProfileNicknameDto
import co.orange.data.service.ProfileService
import javax.inject.Inject

data class ProfileDataSourceImpl
    @Inject
    constructor(
        private val profileService: ProfileService,
    ) : ProfileService {
        override suspend fun geNickname(): BaseResponse<ProfileNicknameDto> = profileService.geNickname()
    }
