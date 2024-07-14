package co.orange.data.repositoryImpl

import co.orange.data.dataSource.ProfileDataSource
import co.orange.domain.entity.response.ProfileNicknameModel
import co.orange.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl
    @Inject
    constructor(
        private val profileDataSource: ProfileDataSource,
    ) : ProfileRepository {
        override suspend fun getNickname(): Result<ProfileNicknameModel> =
            runCatching {
                profileDataSource.getNickname().data.toModel()
            }
    }
