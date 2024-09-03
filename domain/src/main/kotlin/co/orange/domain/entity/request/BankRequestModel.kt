package co.orange.domain.entity.request

data class BankRequestModel(
    val accountName: String,
    val bank: String,
    val accountNumber: String,
)
