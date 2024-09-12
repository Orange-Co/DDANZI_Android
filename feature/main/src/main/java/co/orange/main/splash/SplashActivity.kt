package co.orange.main.splash

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import co.orange.core.R
import co.orange.core.amplitude.AmplitudeManager
import co.orange.core.base.BaseActivity
import co.orange.core.extension.setNavigationBarColorFromResource
import co.orange.core.extension.setStatusBarColorFromResource
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
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import co.orange.main.R as featureR

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(featureR.layout.activity_splash) {
    private val viewModel by viewModels<SplashViewModel>()

    private var serverCheckDialog: ServerCheckDialog? = null

    private val appUpdateManager by lazy { AppUpdateManagerFactory.create(this) }

    private val activityResultLauncher: ActivityResultLauncher<IntentSenderRequest> =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
            if (it.resultCode != RESULT_OK) {
                finishAffinity()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        observeIsServerAvailable()
    }

    override fun onResume() {
        super.onResume()
        checkConnectedNetwork()
    }

    private fun initView() {
        AmplitudeManager.updatePropertyOnce(
            "user_install_date",
            LocalDateTime.now().format(
                DateTimeFormatter.ofPattern(DATE_PATTERN),
            ),
        )
        setStatusBarColorFromResource(R.color.black)
        setNavigationBarColorFromResource(R.color.black)
    }

    private fun checkConnectedNetwork() {
        if (NetworkManager.checkNetworkState(this@SplashActivity)) {
            viewModel.getServerStatusToCheckAvailable()
        } else {
            showNetworkErrorAlertDialog()
        }
    }

    private fun observeIsServerAvailable() {
        viewModel.isServerAvailable.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { isAvailable ->
                if (isAvailable) {
                    checkAppUpdateAvailable()
                } else {
                    serverCheckDialog = ServerCheckDialog()
                    serverCheckDialog?.show(supportFragmentManager, DIALOG_SERVER_CHECK)
                }
            }.launchIn(lifecycleScope)
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

    override fun onDestroy() {
        super.onDestroy()

        serverCheckDialog = null
    }

    companion object {
        private const val DELAY_TIME = 2200L

        private const val DIALOG_SERVER_CHECK = "DIALOG_SERVER_CHECK"

        const val DATE_PATTERN = "yyyy-MM-dd HH:mm:ss"
    }
}
