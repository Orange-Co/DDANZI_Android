package co.orange.main.splash

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import co.orange.core.R
import co.orange.core.base.BaseActivity
import co.orange.core.extension.setNavigationBarColorFromResource
import co.orange.core.extension.setStatusBarColorFromResource
import co.orange.core.extension.toast
import co.orange.main.BuildConfig
import co.orange.main.databinding.ActivitySplashBinding
import co.orange.main.main.MainActivity
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE
import com.google.android.play.core.install.model.UpdateAvailability.UPDATE_AVAILABLE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import co.orange.main.R as featureR

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(featureR.layout.activity_splash) {
    private val appUpdateManager by lazy { AppUpdateManagerFactory.create(this) }

    private val activityResultLauncher: ActivityResultLauncher<IntentSenderRequest> =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
            if (it.resultCode != RESULT_OK) {
                toast("업데이트가 취소되었습니다.")
                finishAffinity()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStatusBarColorFromResource(R.color.black)
        setNavigationBarColorFromResource(R.color.black)
    }

    override fun onResume() {
        super.onResume()
        checkConnectedNetwork()
    }

    private fun checkConnectedNetwork() {
        if (NetworkManager.checkNetworkState(this@SplashActivity)) {
            checkAppUpdateAvailable()
        } else {
            showNetworkErrorAlertDialog()
        }
    }

    private fun checkAppUpdateAvailable() {
        if (BuildConfig.DEBUG) {
            navigateToMain()
        } else {
            appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
                if (isAppUpdateNeeded(appUpdateInfo)) {
                    requestUpdate(appUpdateInfo)
                } else {
                    navigateToMain()
                }
            }.addOnFailureListener {
                toast("업데이트에 실패했습니다.")
                navigateToMain()
            }
        }
    }

    private fun isAppUpdateNeeded(appUpdateInfo: AppUpdateInfo) =
        appUpdateInfo.updateAvailability() == UPDATE_AVAILABLE &&
            appUpdateInfo.isUpdateTypeAllowed(IMMEDIATE)

    private fun requestUpdate(appUpdateInfo: AppUpdateInfo) {
        runCatching {
            appUpdateManager.startUpdateFlowForResult(
                appUpdateInfo,
                activityResultLauncher,
                AppUpdateOptions.newBuilder(IMMEDIATE).build(),
            )
        }
    }

    private fun navigateToMain() {
        lifecycleScope.launch {
            delay(DELAY_TIME)
            Intent(this@SplashActivity, MainActivity::class.java).apply {
                startActivity(this)
            }
            finish()
        }
    }

    private fun showNetworkErrorAlertDialog() =
        AlertDialog.Builder(this).setTitle(R.string.notice)
            .setMessage(R.string.internet_connect_error).setCancelable(false).setPositiveButton(
                R.string.okay,
            ) { _, _ ->
                finishAffinity()
            }.create().show()

    companion object {
        private const val DELAY_TIME = 2200L
    }
}
