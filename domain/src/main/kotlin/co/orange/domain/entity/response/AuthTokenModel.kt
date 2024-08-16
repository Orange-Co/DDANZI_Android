package co.orange.domain.entity.response

data class AuthTokenModel(
    val accesstoken: String,
    val refreshtoken: String,
    val nickname: String,
    val status: String,
)
