package co.orange.ddanzi.di.navigate

import android.content.Context
import android.content.Intent
import co.orange.auth.login.LoginActivity
import co.orange.buy.finished.BuyFinishedActivity
import co.orange.buy.progress.BuyProgressActivity
import co.orange.core.navigation.NavigationManager
import co.orange.main.detail.DetailActivity
import co.orange.main.main.MainActivity
import co.orange.main.push.PushActivity
import co.orange.sell.finished.SellFinishedActivity
import co.orange.sell.info.SellInfoActivity
import co.orange.sell.onboarding.SellOnboardingActivity
import co.orange.setting.bank.BankActivity
import co.orange.setting.delivery.DeliveryActivity
import co.orange.setting.history.HistoryActivity
import co.orange.setting.setting.SettingActivity
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

        override fun toDetailView(productId: String) {
            context.startActivity(DetailActivity.createIntent(context, productId))
        }

        override fun toPushViewWithIntent(
            isBuying: Boolean,
            orderId: String?,
            itemId: String?,
            productName: String?,
            productImage: String?,
            salePrice: Int?,
        ) {
            context.startActivity(
                PushActivity.createIntent(
                    context,
                    isBuying,
                    orderId,
                    itemId,
                    productName,
                    productImage,
                    salePrice,
                ),
            )
        }

        /** To Auth Module**/

        override fun toLoginView() {
            context.startActivity(Intent(context, LoginActivity::class.java))
        }

        /** To Setting Module**/

        override fun toSettingView() {
            context.startActivity(Intent(context, SettingActivity::class.java))
        }

        override fun toHistoryView(type: Int) {
            context.startActivity(HistoryActivity.createIntent(context, type))
        }

        override fun toDeliveryView() {
            context.startActivity(Intent(context, DeliveryActivity::class.java))
        }

        override fun toBankView() {
            context.startActivity(Intent(context, BankActivity::class.java))
        }

        /** To Buy Module**/

        override fun toBuyProgressView(
            productId: String,
            optionList: ArrayList<Int>,
        ) {
            context.startActivity(BuyProgressActivity.createIntent(context, productId, optionList))
        }

        override fun toBuyFinishedView(orderId: String) {
            context.startActivity(BuyFinishedActivity.createIntent(context, orderId))
        }

        /** To Sell Module**/

        override fun toSellOnboardingView() {
            context.startActivity(Intent(context, SellOnboardingActivity::class.java))
        }

        override fun toSellFinishedView(
            itemId: String,
            productName: String,
            productImage: String,
            salePrice: Int,
        ) {
            context.startActivity(
                SellFinishedActivity.createIntent(
                    context,
                    itemId,
                    productName,
                    productImage,
                    salePrice,
                ),
            )
        }

        override fun toSellInfoView(itemId: String) {
            context.startActivity(SellInfoActivity.createIntent(context, itemId))
        }
    }
