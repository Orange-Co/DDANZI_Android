package co.orange.domain.enums

enum class BankType(val displayName: String, val code: String) {
    KOOKMIN("국민은행", "KOOKMIN"),
    NONGHYUP("농협", "NONGHYUP"),
    KAKAOBANK("카카오뱅크", "KAKAOBANK"),
    SINHAN("신한은행", "SINHAN"),
    WOORI("우리은행", "WOORI"),
    IBK("기업은행", "IBK"),
    HANA("하나은행", "HANA"),
    TOSS("토스뱅크", "TOSS"),
    KDB("산업은행", "KDB"),
    POST("우체국", "POST"),
    SC("SC 제일은행", "SC"),
    BUSAN("부산은행", "BUSAN"),
    GWANGJU("광주은행", "GWANGJU"),
    JEONBUK("전북은행", "JEONBUK"),
    DAEGU("IM 뱅크(대구)", "IM_BANK"),
    KYONGNAM("경남은행", "KYONGNAM"),
    JEJU("제주은행", "JEJU"),
    SUHYUP("수협", "SUHYUP"),
    K_BANK("케이뱅크", "K_BANK"),
    SAE_MAUL("새마을금고", "SAE_MAUL"),
    SHINHYUP("신협", "SHINHYUP"),
    NONGCHUK("농축협", "NONGCHUK"),
    CITI("씨티은행", "CITI"),
    SAVING("저축은행", "SAVING"),
    HSBC("HSBC 은행", "HSBC"),
    DEUTSCHE("도이치 은행", "DEUTSCHE"),
    ;

    companion object {
        fun fromCode(code: String): String? {
            return values().find { it.code == code }?.displayName
        }
    }
}
