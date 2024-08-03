package co.orange.data.dto.response

import co.orange.domain.entity.response.IamportCertificationModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IamportCertificationDto(
    @SerialName("imp_uid")
    val impUid: String,
    @SerialName("merchant_uid")
    val merchantUid: String?,
    @SerialName("pg_uid")
    val pgUid: String?,
    @SerialName("pg_provider")
    val pgProvider: String,
    @SerialName("name")
    val name: String?,
    @SerialName("gender")
    val gender: String?,
    @SerialName("birthday")
    val birthday: String?,
    @SerialName("foreigner")
    val foreigner: Boolean,
    @SerialName("phone")
    val phone: String?,
    @SerialName("carrier")
    val carrier: String?,
    @SerialName("certified")
    val certified: Boolean?,
    @SerialName("unique_key")
    val uniqueKey: String?,
    @SerialName("unique_in_site")
    val uniqueInSite: String?,
    @SerialName("origin")
    val origin: String?,
    @SerialName("foreigner_v2")
    val foreignerV2: Boolean?,
) {
    fun toModel() =
        IamportCertificationModel(
            impUid,
            merchantUid,
            pgUid,
            pgProvider,
            name,
            gender,
            birthday,
            foreigner,
            phone,
            carrier,
            certified,
            uniqueKey,
            uniqueInSite,
            origin,
            foreignerV2,
        )
}
