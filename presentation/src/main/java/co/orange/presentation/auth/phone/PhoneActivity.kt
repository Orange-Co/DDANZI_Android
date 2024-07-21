package co.orange.presentation.auth.phone

import android.os.Bundle
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
            settings.javaScriptEnabled = true
//            settings.javaScriptCanOpenWindowsAutomatically = true
//            settings.cacheMode = WebSettings.LOAD_NO_CACHE
//            setLayerType(View.LAYER_TYPE_HARDWARE, null)
//            scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
//            isScrollbarFadingEnabled = true
//            webChromeClient = myChromeClient()
//            webViewClient = WebViewClientClass()
            loadUrl("file:///android_asset/web_auth_phone.html")
        }
    }
}
