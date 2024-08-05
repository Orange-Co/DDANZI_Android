package co.orange.presentation.setting.delivery

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.webkit.WebViewAssetLoader
import androidx.webkit.WebViewClientCompat
import co.orange.core.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivityAddressBinding

@AndroidEntryPoint
class AddressActivity : BaseActivity<ActivityAddressBinding>(R.layout.activity_address) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setWebView()
        loadWebView()
    }

    private fun setWebView() {
        binding.webDaumAddress.apply {
            with(settings) {
                javaScriptEnabled = true
                javaScriptCanOpenWindowsAutomatically = true
                cacheMode = WebSettings.LOAD_NO_CACHE
                allowFileAccess = false
                allowContentAccess = false
            }
            setLayerType(View.LAYER_TYPE_HARDWARE, null)
            scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
            isScrollbarFadingEnabled = true
            addJavascriptInterface(AddressBridge(this@AddressActivity), ADDRESS_BRIDGE)
        }
    }

    private fun loadWebView() {
        with(binding.webDaumAddress) {
            webViewClient =
                FileWebViewClient(
                    WebViewAssetLoader.Builder()
                        .addPathHandler(
                            "/$PATH/",
                            WebViewAssetLoader.AssetsPathHandler(this@AddressActivity),
                        )
                        .setDomain(DOMAIN)
                        .build(),
                )
            loadUrl("https://$DOMAIN/$PATH/web_daum_address.html")
        }
    }

    private class FileWebViewClient(private val assetLoader: WebViewAssetLoader) :
        WebViewClientCompat() {
        override fun shouldInterceptRequest(
            view: WebView?,
            request: WebResourceRequest?,
        ): WebResourceResponse? = request?.url?.let { assetLoader.shouldInterceptRequest(it) }

        @Deprecated("Deprecated in Java")
        override fun shouldInterceptRequest(
            view: WebView?,
            url: String?,
        ): WebResourceResponse? = assetLoader.shouldInterceptRequest(Uri.parse(url))
    }

    companion object {
        private const val ADDRESS_BRIDGE = "address_bridge"
        private const val DOMAIN = "address.finder.com"
        private const val PATH = "assets"
        private const val ACTION_ADDRESS_ACTIVITY =
            "co.orange.presentation.setting.delivery.ADDRESS"

        private var launcher: ActivityResultLauncher<Bundle>? = null
        private val contract: ActivityResultContract<Bundle, Bundle>
            get() =
                object : ActivityResultContract<Bundle, Bundle>() {
                    override fun createIntent(
                        context: Context,
                        input: Bundle,
                    ): Intent = Intent(ACTION_ADDRESS_ACTIVITY)

                    override fun parseResult(
                        resultCode: Int,
                        intent: Intent?,
                    ): Bundle =
                        when (resultCode) {
                            RESULT_CANCELED -> Bundle.EMPTY
                            else -> intent?.extras ?: Bundle.EMPTY
                        }
                }
        private var action: ((Bundle) -> Unit)? = null

        @JvmStatic
        fun open(onComplete: (Bundle) -> Unit) {
            action = onComplete
            launcher?.launch(Bundle())
        }

        @JvmStatic
        fun register(activity: ComponentActivity) {
            launcher =
                activity.registerForActivityResult(contract) { bundle ->
                    action?.invoke(bundle)
                }
        }

        @JvmStatic
        fun unregister() {
            action = null
            launcher = null
        }
    }
}
