package co.orange.domain.repository

import co.orange.domain.entity.request.AddressRequestModel
import co.orange.domain.entity.response.AddressModel
import co.orange.domain.entity.response.SettingInfoModel

interface SettingRepository {
    suspend fun getSettingInfo(): Result<SettingInfoModel>

    suspend fun postToAddAddress(request: AddressRequestModel): Result<AddressModel>

    suspend fun putToModAddress(
        addressId: Long,
        request: AddressRequestModel,
    ): Result<AddressModel>

    suspend fun getUserAddress(): Result<AddressModel>

    suspend fun deleteUserAddress(): Result<Boolean>
}
