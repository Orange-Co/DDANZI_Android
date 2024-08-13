package co.orange.domain.entity.response

data class ReissueTokenModel(
    val accessToken: String,
    val refreshToken: String,
    val userId: Long,
)
