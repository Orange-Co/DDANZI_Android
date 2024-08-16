package co.orange.presentation.splash

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import co.orange.core.base.BaseActivity
import co.orange.core.extension.setNavigationBarColorFromResource
import co.orange.core.extension.setStatusBarColorFromResource
import co.orange.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivitySplashBinding

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStatusBarColor()
        setNavigationBarColor()
        checkConnectedNetwork()
    }

    private fun setStatusBarColor() = setStatusBarColorFromResource(R.color.black)

    private fun setNavigationBarColor() = setNavigationBarColorFromResource(R.color.black)

    private fun checkConnectedNetwork() {
        lifecycleScope.launch {
            delay(DELAY_TIME)
            if (NetworkManager.checkNetworkState(this@SplashActivity)) {
                Intent(this@SplashActivity, MainActivity::class.java).apply {
                    startActivity(this)
                }
                finish()
            } else {
                showNetworkErrorAlertDialog()
            }
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
