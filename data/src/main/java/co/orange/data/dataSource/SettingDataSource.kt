package co.orange.data.dataSource

import co.orange.data.dto.BaseResponse
import co.orange.data.dto.request.AddressRequestDto
import co.orange.data.dto.request.BankRequestDto
import co.orange.data.dto.response.AddressDto
import co.orange.data.dto.response.BankDto
import co.orange.data.dto.response.NicknameDto
import co.orange.data.dto.response.SettingInfoDto

interface SettingDataSource {
    suspend fun getSettingInfo(): BaseResponse<SettingInfoDto>

    suspend fun postToAddAddress(request: AddressRequestDto): BaseResponse<AddressDto>

    suspend fun putToModAddress(
        addressId: Long,
        request: AddressRequestDto,
    ): BaseResponse<AddressDto>

    suspend fun getUserAddress(): BaseResponse<AddressDto>

    suspend fun deleteUserAddress(addressId: Long): BaseResponse<Boolean>

    suspend fun postUserLogout(): BaseResponse<Boolean>

    suspend fun deleteToUserQuit(): BaseResponse<NicknameDto>

    suspend fun postToAddBank(request: BankRequestDto): BaseResponse<BankDto>

    suspend fun putToModBank(
        accountId: Long,
        request: BankRequestDto,
    ): BaseResponse<BankDto>
}
