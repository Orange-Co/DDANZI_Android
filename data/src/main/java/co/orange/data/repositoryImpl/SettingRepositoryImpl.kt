package co.orange.data.repositoryImpl

import co.orange.data.dataSource.SettingDataSource
import co.orange.data.dto.request.AddressRequestDto.Companion.toDto
import co.orange.data.dto.request.BankRequestDto.Companion.toDto
import co.orange.domain.entity.request.AddressRequestModel
import co.orange.domain.entity.request.BankRequestModel
import co.orange.domain.entity.response.AddressModel
import co.orange.domain.entity.response.BankModel
import co.orange.domain.entity.response.NicknameModel
import co.orange.domain.entity.response.SettingInfoModel
import co.orange.domain.repository.SettingRepository
import javax.inject.Inject

class SettingRepositoryImpl
    @Inject
    constructor(
        private val settingDataSource: SettingDataSource,
    ) : SettingRepository {
        override suspend fun getSettingInfo(): Result<SettingInfoModel> =
            runCatching {
                settingDataSource.getSettingInfo().data.toModel()
            }

        override suspend fun postToAddAddress(request: AddressRequestModel): Result<AddressModel> =
            runCatching {
                settingDataSource.postToAddAddress(request.toDto()).data.toModel()
            }

        override suspend fun putToModAddress(
            addressId: Long,
            request: AddressRequestModel,
        ): Result<AddressModel> =
            runCatching {
                settingDataSource.putToModAddress(addressId, request.toDto()).data.toModel()
            }

        override suspend fun getUserAddress(): Result<AddressModel> =
            runCatching {
                settingDataSource.getUserAddress().data.toModel()
            }

        override suspend fun deleteUserAddress(addressId: Long): Result<Boolean> =
            runCatching {
                settingDataSource.deleteUserAddress(addressId).data
            }

        override suspend fun postUserLogout(): Result<Boolean> =
            runCatching {
                settingDataSource.postUserLogout().data
            }

        override suspend fun deleteToUserQuit(): Result<NicknameModel> =
            runCatching {
                settingDataSource.deleteToUserQuit().data.toModel()
            }

        override suspend fun postToAddBank(request: BankRequestModel): Result<BankModel> =
            runCatching {
                settingDataSource.postToAddBank(request.toDto()).data.toModel()
            }

        override suspend fun putToModBank(
            accountId: Long,
            request: BankRequestModel,
        ): Result<BankModel> =
            runCatching {
                settingDataSource.putToModBank(accountId, request.toDto()).data.toModel()
            }

        override suspend fun getUserBank(): Result<BankModel> =
            runCatching {
                settingDataSource.getUserBank().data.toModel()
            }
    }
