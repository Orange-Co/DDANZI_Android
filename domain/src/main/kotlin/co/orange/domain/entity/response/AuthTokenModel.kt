package co.orange.domain.entity.response

data class AuthTokenModel(
    val accessToken: String,
    val refreshToken: String,
    val userId: Long,
)
