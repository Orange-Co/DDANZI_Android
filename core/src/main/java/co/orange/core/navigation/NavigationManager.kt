package co.orange.core.navigation

interface NavigationManager {
    /** To Main Module**/
    fun toMainViewWIthClearing()

    fun toDeliveryView()

    fun toPushViewWithIntent(
        isBuying: Boolean,
        orderId: String?,
        itemId: String?,
        productName: String?,
        productImage: String?,
        salePrice: Int?,
    )

    /** To Auth Module**/

    /** To Setting Module**/

    /** To Buy Module**/

    /** To Sell Module**/
}
