package co.orange.domain.entity.response

data class IamportTokenModel(
    val code: Int?,
    val message: String?,
    val response: IamportAuthModel?,
) {
    data class IamportAuthModel(
        val accessToken: String,
        val expiredAt: Int,
        val now: Int,
    )
}
