package co.orange.presentation.setting.delivery

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.webkit.JavascriptInterface

class AddressBridge(private val activity: Activity) {
    @JavascriptInterface
    fun result(
        address: String,
        zipCode: String,
    ) {
        with(activity) {
            setResult(
                RESULT_OK,
                Intent().apply {
                    putExtra(ADDRESS, address)
                    putExtra(ZIPCODE, zipCode)
                },
            )
            finish()
        }
    }

    companion object {
        const val ADDRESS = "address"
        const val ZIPCODE = "zipcode"
    }
}
