package co.orange.domain.entity.request

data class SignUpRequestModel(
    val name: String,
    val phone: String,
    val birth: String,
    val sex: String,
    val isAgreedMarketingTerm: Boolean,
)
