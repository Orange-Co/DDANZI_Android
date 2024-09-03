package co.orange.data.dto.request

import co.orange.domain.entity.request.BankRequestModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BankRequestDto(
    @SerialName("accountName")
    val accountName: String,
    @SerialName("bank")
    val bank: String,
    @SerialName("accountNumber")
    val accountNumber: String,
) {
    companion object {
        fun BankRequestModel.toDto() = BankRequestDto(accountName, bank, accountNumber)
    }
}
