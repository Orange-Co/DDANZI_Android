package co.orange.domain.repository

import co.orange.domain.entity.request.AddressRequestModel
import co.orange.domain.entity.request.BankRequestModel
import co.orange.domain.entity.response.AddressModel
import co.orange.domain.entity.response.BankModel
import co.orange.domain.entity.response.NicknameModel
import co.orange.domain.entity.response.SettingInfoModel

interface SettingRepository {
    suspend fun getSettingInfo(): Result<SettingInfoModel>

    suspend fun postToAddAddress(request: AddressRequestModel): Result<AddressModel>

    suspend fun putToModAddress(
        addressId: Long,
        request: AddressRequestModel,
    ): Result<AddressModel>

    suspend fun getUserAddress(): Result<AddressModel>

    suspend fun deleteUserAddress(addressId: Long): Result<Boolean>

    suspend fun postUserLogout(): Result<Boolean>

    suspend fun deleteToUserQuit(): Result<NicknameModel>

    suspend fun postToAddBank(request: BankRequestModel): Result<BankModel>

    suspend fun putToModBank(
        accountId: Long,
        request: BankRequestModel,
    ): Result<BankModel>
}
