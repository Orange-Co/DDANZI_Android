package co.orange.domain.repository

import co.orange.domain.entity.response.ProfileNicknameModel

interface ProfileRepository {
    suspend fun getNickname(): Result<ProfileNicknameModel>
}
