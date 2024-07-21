package co.orange.presentation.auth.phone

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import co.orange.core.base.BaseActivity
import com.iamport.sdk.domain.core.Iamport
import dagger.hilt.android.AndroidEntryPoint
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivityPhoneBinding

@AndroidEntryPoint
class PhoneActivity : BaseActivity<ActivityPhoneBinding>(R.layout.activity_phone) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Iamport.init(this)
        initWebView()
    }

    private fun initWebView() {
        binding.webPhoneAuth.apply {
//            addJavascriptInterface(
//                SmsAuthFactory(
//                    this@PhoneActivity,
//                ),
//                "AndroidBridge",
//            )
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
}
