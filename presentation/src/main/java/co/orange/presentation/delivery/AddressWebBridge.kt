package co.orange.presentation.delivery

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.webkit.JavascriptInterface

class AddressWebBridge(private val activity: Activity) {
    @JavascriptInterface
    fun result(
        address: String,
        zipCode: String,
    ) {
        with(activity) {
            setResult(
                RESULT_OK,
                Intent().apply {
                    putExtra(EXTRA_ADDRESS, address)
                    putExtra(EXTRA_ZIPCODE, zipCode)
                },
            )
            finish()
        }
    }

    companion object {
        const val EXTRA_ADDRESS = "address"
        const val EXTRA_ZIPCODE = "zipcode"
    }
}
