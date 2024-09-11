package co.orange.core.navigation

import android.content.Context

interface NavigationManager {
    /** To Main Module**/
    fun toMainViewWIthClearing(context: Context)

    fun toDetailView(
        context: Context,
        productId: String,
    )

    fun toAlarmRequestViewWithIntent(
        context: Context,
        isBuying: Boolean,
        orderId: String?,
        itemId: String?,
        productName: String?,
        productImage: String?,
        salePrice: Int?,
    )

    /** To Auth Module**/

    fun toLoginView(
        context: Context,
        property: String,
    )

    /** To Setting Module**/

    fun toSettingView(context: Context)

    fun toHistoryView(
        context: Context,
        type: Int,
    )

    fun toDeliveryView(context: Context)

    fun toBankView(context: Context)

    /** To Buy Module**/

    fun toBuyProgressView(
        context: Context,
        productId: String,
        optionList: ArrayList<Int>,
    )

    fun toBuyFinishedView(
        context: Context,
        orderId: String,
    )

    fun toBuyInfoView(
        context: Context,
        orderId: String,
    )

    /** To Sell Module**/

    fun toSellOnboardingView(context: Context)

    fun toSellFinishedView(
        context: Context,
        itemId: String,
        productName: String,
        productImage: String,
        salePrice: Int,
    )

    fun toSellInfoView(
        context: Context,
        itemId: String,
    )

    fun toSellConfirmView(
        context: Context,
        orderId: String,
    )
}
