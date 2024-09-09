package co.orange.data.local

interface UserSharedPref {
    var accessToken: String
    var refreshToken: String
    var deviceToken: String
    var userName: String
    var userPhone: String
    var userStatus: String

    fun clearInfo()
}
