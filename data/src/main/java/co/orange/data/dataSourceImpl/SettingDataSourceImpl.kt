package co.orange.data.dataSourceImpl

import co.orange.data.dataSource.SettingDataSource
import co.orange.data.dto.BaseResponse
import co.orange.data.dto.request.AddressRequestDto
import co.orange.data.dto.request.BankRequestDto
import co.orange.data.dto.response.AddressDto
import co.orange.data.dto.response.BankDto
import co.orange.data.dto.response.NicknameDto
import co.orange.data.dto.response.SettingInfoDto
import co.orange.data.service.SettingService
import javax.inject.Inject

data class SettingDataSourceImpl
    @Inject
    constructor(
        private val settingService: SettingService,
    ) : SettingDataSource {
        override suspend fun getSettingInfo(): BaseResponse<SettingInfoDto> = settingService.getSettingInfo()

        override suspend fun postToAddAddress(request: AddressRequestDto): BaseResponse<AddressDto> =
            settingService.postToAddAddress(request)

        override suspend fun putToModAddress(
            addressId: Long,
            request: AddressRequestDto,
        ): BaseResponse<AddressDto> = settingService.putToModAddress(addressId, request)

        override suspend fun getUserAddress(): BaseResponse<AddressDto> = settingService.getUserAddress()

        override suspend fun deleteUserAddress(addressId: Long): BaseResponse<Boolean> = settingService.deleteUserAddress(addressId)

        override suspend fun postUserLogout(): BaseResponse<Boolean> = settingService.postUserLogout()

        override suspend fun deleteToUserQuit(): BaseResponse<NicknameDto> = settingService.deleteToUserQuit()

        override suspend fun postToAddBank(request: BankRequestDto): BaseResponse<BankDto> = settingService.postToAddBank(request)

        override suspend fun putToModBank(
            accountId: Long,
            request: BankRequestDto,
        ): BaseResponse<BankDto> = settingService.putToModBank(accountId, request)
    }
