package co.orange.data.service

import co.orange.data.dto.BaseResponse
import co.orange.data.dto.request.AddressRequestDto
import co.orange.data.dto.request.BankRequestDto
import co.orange.data.dto.response.AddressDto
import co.orange.data.dto.response.BankDto
import co.orange.data.dto.response.NicknameDto
import co.orange.data.dto.response.SettingInfoDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface SettingService {
    @GET("/api/v1/mypage/setting")
    suspend fun getSettingInfo(): BaseResponse<SettingInfoDto>

    @POST("/api/v1/mypage/setting/address")
    suspend fun postToAddAddress(
        @Body request: AddressRequestDto,
    ): BaseResponse<AddressDto>

    @PUT("/api/v1/mypage/setting/address/{id}")
    suspend fun putToModAddress(
        @Path("id") addressId: Long,
        @Body request: AddressRequestDto,
    ): BaseResponse<AddressDto>

    @GET("/api/v1/mypage/setting/address")
    suspend fun getUserAddress(): BaseResponse<AddressDto>

    @DELETE("/api/v1/mypage/setting/address/{id}")
    suspend fun deleteUserAddress(
        @Path("id") addressId: Long,
    ): BaseResponse<Boolean>

    @POST("/api/v1/auth/logout")
    suspend fun postUserLogout(): BaseResponse<Boolean>

    @DELETE("/api/v1/auth/withdraw")
    suspend fun deleteToUserQuit(): BaseResponse<NicknameDto>

    @POST("/api/v1/mypage/setting/account")
    suspend fun postToAddBank(
        @Body request: BankRequestDto,
    ): BaseResponse<BankDto>

    @PUT("/api/v1/mypage/setting/account/{id}")
    suspend fun putToModBank(
        @Path("id") accountId: Long,
        @Body request: BankRequestDto,
    ): BaseResponse<BankDto>

    @GET("/api/v1/mypage/setting/account")
    suspend fun getUserBank(): BaseResponse<BankDto>
}
