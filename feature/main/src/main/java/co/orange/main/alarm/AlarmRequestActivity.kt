package co.orange.main.alarm

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import co.orange.core.amplitude.AmplitudeManager
import co.orange.core.base.BaseActivity
import co.orange.core.extension.initOnBackPressedListener
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.navigation.NavigationManager
import co.orange.main.databinding.ActivityAlarmRequestBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import co.orange.main.R as featureR

@AndroidEntryPoint
class AlarmRequestActivity :
    BaseActivity<ActivityAlarmRequestBinding>(featureR.layout.activity_alarm_request) {
    @Inject
    lateinit var navigationManager: NavigationManager

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initOnBackPressedListener(binding.root)
        initExitBtnListener()
        initAlarmBtnListener()
        setRequestPermissionLauncher()
    }

    override fun onResume() {
        super.onResume()

        checkReturnedWIthAlarm()
    }

    private fun initExitBtnListener() {
        with(binding) {
            btnExit.setOnSingleClickListener {
                AmplitudeManager.trackEvent("click_push_refuse")
                checkIsBuyOrSell()
            }
            btnLater.setOnSingleClickListener {
                AmplitudeManager.trackEvent("click_push_refuse")
                checkIsBuyOrSell()
            }
        }
    }

    private fun initAlarmBtnListener() {
        binding.btnAlarm.setOnSingleClickListener {
            requestAlarmPermission()
        }
    }

    private fun setRequestPermissionLauncher() {
        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    AmplitudeManager.updateProperty("user_push", "enabled")
                } else {
                    AmplitudeManager.updateProperty("user_push", "disabled")
                }
                checkIsBuyOrSell()
            }
    }

    private fun requestAlarmPermission() {
        if (isAlreadyRejectedPermission()) {
            // 이미 권한을 거절한 경우 권한 설정 화면으로 이동
            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.parse("package:" + this@AlarmRequestActivity.packageName)
                startActivity(this)
            }
        } else {
            // 처음 권한 요청을 할 경우 권한 동의 팝업 표시
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun isAlreadyRejectedPermission(): Boolean =
        ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.POST_NOTIFICATIONS,
        )

    private fun checkReturnedWIthAlarm() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.POST_NOTIFICATIONS,
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            checkIsBuyOrSell()
        }
    }

    private fun checkIsBuyOrSell() {
        if (intent.getBooleanExtra(EXTRA_IS_BUYING, true)) {
            AmplitudeManager.trackEvent("view_purchase_push")
            navigateToBuyFinishedActivity()
        } else {
            AmplitudeManager.trackEvent("view_sell_push")
            navigateToSellFinishedActivity()
        }
    }

    private fun navigateToBuyFinishedActivity() {
        navigationManager.toBuyFinishedView(
            this,
            intent.getStringExtra(EXTRA_ORDER_ID).orEmpty(),
        )
        finish()
    }

    private fun navigateToSellFinishedActivity() {
        navigationManager.toSellFinishedView(
            this,
            intent.getStringExtra(EXTRA_ITEM_ID).orEmpty(),
            intent.getStringExtra(EXTRA_PRODUCT_NAME).orEmpty(),
            intent.getStringExtra(EXTRA_PRODUCT_IMAGE).orEmpty(),
            intent.getIntExtra(EXTRA_SALE_PRICE, 0),
        )
        finish()
    }

    companion object {
        private const val EXTRA_IS_BUYING = "EXTRA_IS_BUYING"
        private const val EXTRA_ORDER_ID = "EXTRA_ORDER_ID"
        private const val EXTRA_ITEM_ID = "EXTRA_ITEM_ID"
        private const val EXTRA_PRODUCT_NAME = "EXTRA_PRODUCT_NAME"
        private const val EXTRA_PRODUCT_IMAGE = "EXTRA_PRODUCT_IMAGE"
        private const val EXTRA_SALE_PRICE = "EXTRA_SALE_PRICE"

        @JvmStatic
        fun createIntent(
            context: Context,
            isBuying: Boolean,
            orderId: String?,
            itemId: String?,
            productName: String?,
            productImage: String?,
            salePrice: Int?,
        ): Intent =
            Intent(context, AlarmRequestActivity::class.java).apply {
                putExtra(EXTRA_IS_BUYING, isBuying)
                putExtra(EXTRA_ORDER_ID, orderId)
                putExtra(EXTRA_ITEM_ID, itemId)
                putExtra(EXTRA_PRODUCT_NAME, productName)
                putExtra(EXTRA_PRODUCT_IMAGE, productImage)
                putExtra(EXTRA_SALE_PRICE, salePrice)
            }
    }
}
