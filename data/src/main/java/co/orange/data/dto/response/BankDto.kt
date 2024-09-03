package co.orange.data.dto.response

import co.orange.domain.entity.response.BankModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BankDto(
    @SerialName("accountId")
    val accountId: Long? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("bank")
    val bank: String? = null,
    @SerialName("accountNumber")
    val accountNumber: String? = null,
) {
    fun toModel() = BankModel(accountId, name, bank, accountNumber)
}
