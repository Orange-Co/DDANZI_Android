package co.orange.data.local

interface UserSharedPref {
    var accessToken: String
    var refreshToken: String
    var userId: Long

    fun clearInfo()
}
