package co.orange.domain.entity.request

data class AuthRequestModel(
    val token: String,
    val type: String,
)
