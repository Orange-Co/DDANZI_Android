package co.orange.data.dto.response

import co.orange.domain.entity.response.IamportCertificationModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IamportCertificationDto(
    @SerialName("imp_uid")
    val impUid: String,
    @SerialName("merchant_uid")
    val merchantUid: String? = null,
    @SerialName("pg_uid")
    val pgUid: String? = null,
    @SerialName("pg_provider")
    val pgProvider: String,
    @SerialName("name")
    val name: String? = null,
    @SerialName("gender")
    val gender: String? = null,
    @SerialName("birthday")
    val birthday: String? = null,
    @SerialName("foreigner")
    val foreigner: Boolean,
    @SerialName("phone")
    val phone: String? = null,
    @SerialName("carrier")
    val carrier: String? = null,
    @SerialName("certified")
    val certified: Boolean? = null,
    @SerialName("unique_key")
    val uniqueKey: String? = null,
    @SerialName("unique_in_site")
    val uniqueInSite: String? = null,
    @SerialName("origin")
    val origin: String? = null,
    @SerialName("foreigner_v2")
    val foreignerV2: Boolean? = null,
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
