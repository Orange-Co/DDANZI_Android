package co.orange.domain.entity.response

data class IamportTokenModel(
    val accessToken: String,
    val expiredAt: Int,
    val now: Int,
)
