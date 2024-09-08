package co.orange.ddanzi.di.navigate

import android.content.Context
import android.content.Intent
import co.orange.core.navigation.NavigationManager
import co.orange.main.main.MainActivity
import co.orange.main.push.PushActivity
import co.orange.setting.delivery.DeliveryActivity
import javax.inject.Inject

class NavigationManagerImpl
    @Inject
    constructor(
        private val context: Context,
    ) : NavigationManager {
        /** To Main Module**/
        override fun toMainViewWIthClearing() {
            Intent(context, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                context.startActivity(this)
            }
        }

        override fun toDeliveryView() {
            Intent(context, DeliveryActivity::class.java).apply {
                context.startActivity(this)
            }
        }

        override fun toPushViewWithIntent(
            isBuying: Boolean,
            orderId: String?,
            itemId: String?,
            productName: String?,
            productImage: String?,
            salePrice: Int?,
        ) {
            PushActivity.createIntent(
                context,
                isBuying,
                orderId,
                itemId,
                productName,
                productImage,
                salePrice,
            )
                .apply { context.startActivity(this) }
        }

        /** To Auth Module**/

        /** To Setting Module**/

        /** To Buy Module**/

        /** To Sell Module**/
    }
