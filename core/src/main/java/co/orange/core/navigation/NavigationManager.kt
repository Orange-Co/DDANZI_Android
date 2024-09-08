package co.orange.core.navigation

interface NavigationManager {
    /** To Main Module**/
    fun toMainViewWIthClearing()

    fun toDetailView(productId: String)

    fun toPushViewWithIntent(
        isBuying: Boolean,
        orderId: String?,
        itemId: String?,
        productName: String?,
        productImage: String?,
        salePrice: Int?,
    )

    /** To Auth Module**/

    fun toLoginView()

    /** To Setting Module**/

    fun toSettingView()

    fun toHistoryView(type: Int)

    fun toDeliveryView()

    /** To Buy Module**/

    fun toBuyProgressView(
        productId: String,
        optionList: ArrayList<Int>,
    )

    fun toBuyFinishedView(orderId: String)

    /** To Sell Module**/

    fun toSellOnboardingView()

    fun toSellFinishedView(
        itemId: String,
        productName: String,
        productImage: String,
        salePrice: Int,
    )

    fun toSellInfoView(itemId: String)
}
