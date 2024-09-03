package co.orange.data.dto.response

import co.orange.domain.entity.response.BankModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BankDto(
    @SerialName("accountId")
    val accountId: Long,
    @SerialName("name")
    val name: String,
    @SerialName("bank")
    val bank: String,
    @SerialName("accountNumber")
    val accountNumber: String,
) {
    fun toModel() = BankModel(accountId, name, bank, accountNumber)
}
