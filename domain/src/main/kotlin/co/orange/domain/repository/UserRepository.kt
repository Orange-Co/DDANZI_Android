package co.orange.domain.repository

interface UserRepository {
    fun getAccessToken(): String

    fun getRefreshToken(): String

    fun setTokens(
        accessToken: String,
        refreshToken: String,
    )

    fun getDeviceToken(): String

    fun setDeviceToken(deviceToken: String)

    fun getUserName(): String

    fun getUserPhone(): String

    fun setUserInfo(
        userName: String,
        userPhone: String,
    )

    fun clearInfo()
}
