package co.orange.domain.enums

enum class PaymentMethod(val methodName: String) {
    CARD("card"),
    NAVERPAY_CARD("naverpay_card"),
    KAKAOPAY("kakaopay"),
    SAMSUNGPAY("samsungpay"),
    PHONE("phone"),
    ;

    companion object {
        fun fromId(id: Int): PaymentMethod? {
            return values().getOrNull(id)
        }
    }
}
