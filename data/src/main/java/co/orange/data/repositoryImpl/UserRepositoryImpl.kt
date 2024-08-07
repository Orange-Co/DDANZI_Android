package co.orange.data.repositoryImpl

import co.orange.data.local.UserSharedPref
import co.orange.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl
    @Inject
    constructor(
        private val userSharedPref: UserSharedPref,
    ) : UserRepository {
        override fun getAccessToken(): String = userSharedPref.accessToken

        override fun getRefreshToken(): String = userSharedPref.refreshToken

        override fun setTokens(
            accessToken: String,
            refreshToken: String,
        ) {
            userSharedPref.accessToken = accessToken
            userSharedPref.refreshToken = refreshToken
        }

        override fun getDeviceToken(): String = userSharedPref.deviceToken

        override fun setDeviceToken(deviceToken: String) {
            userSharedPref.deviceToken = deviceToken
        }

        override fun getUserName(): String = userSharedPref.userName

        override fun getUserPhone(): String = userSharedPref.userPhone

        override fun setUserInfo(
            userName: String,
            userPhone: String,
        ) {
            userSharedPref.userName = userName
            userSharedPref.userPhone = userPhone
        }

        override fun clearInfo() {
            userSharedPref.clearInfo()
        }
    }
