package co.orange.presentation.auth.phone

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import co.orange.core.base.BaseActivity
import com.iamport.sdk.domain.core.Iamport
import dagger.hilt.android.AndroidEntryPoint
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivitySmsAuthBinding

@AndroidEntryPoint
class SmsAuthActivity : BaseActivity<ActivitySmsAuthBinding>(R.layout.activity_sms_auth) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Iamport.init(this)
        initWebView()
    }

    private fun initWebView() {
        binding.webPhoneAuth.apply {
            addJavascriptInterface(
                SmsAuthFactory(
                    this@SmsAuthActivity,
                ),
                ANDROID_BRIDGE,
            )
            settings.apply {
                javaScriptEnabled = true
                javaScriptCanOpenWindowsAutomatically = true
                cacheMode = WebSettings.LOAD_NO_CACHE
            }
            setLayerType(View.LAYER_TYPE_HARDWARE, null)
            scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
            isScrollbarFadingEnabled = true
//            webChromeClient = myChromeClient()
//            webViewClient = WebViewClientClass()
            loadUrl("file:///android_asset/web_auth_phone.html")
        }
    }

    override fun onKeyDown(
        keyCode: Int,
        event: KeyEvent?,
    ): Boolean {
        binding.webPhoneAuth.apply {
            if ((keyCode == KeyEvent.KEYCODE_BACK) && this.canGoBack()) {
                this.goBack()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    companion object {
        const val ANDROID_BRIDGE = "AndroidBridge"

        @JvmStatic
        fun createIntent(context: Context): Intent =
            Intent(context, SmsAuthFactory::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
    }
}
