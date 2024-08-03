package co.orange.domain.entity.response

data class IamportCertificationModel(
    val impUid: String,
    val merchantUid: String?,
    val pgUid: String?,
    val pgProvider: String,
    val name: String?,
    val gender: String?,
    val birthday: String?,
    val foreigner: Boolean,
    val phone: String?,
    val carrier: String?,
    val certified: Boolean?,
    val uniqueKey: String?,
    val uniqueInSite: String?,
    val origin: String?,
    val foreignerV2: Boolean?,
)
