package co.orange.domain.entity.request

data class AuthRequestModel(
    val token: String,
    val type: String,
    val deviceToken: String,
    val deviceType: String,
    val fcmToken: String,
)
