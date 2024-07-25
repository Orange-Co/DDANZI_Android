package co.orange.presentation.auth.phone

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class SmsAuthWebViewClient : WebViewClient() {
    override fun shouldOverrideUrlLoading(
        view: WebView?,
        request: WebResourceRequest?,
    ): Boolean {
        val url: String = request?.url.toString()
        view?.loadUrl(url)
        return super.shouldOverrideUrlLoading(view, request)
    }
}
