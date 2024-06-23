package co.orange.domain.entity.response

data class AccountModel(
    val accountId: Long,
    val name: String,
    val bank: String,
    val accountNumber: String,
)
