package co.orange.presentation.auth.phone

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.webkit.JavascriptInterface
import co.orange.presentation.auth.phone.PhoneActivity.Companion.EXTRA_IMP_UID
import co.orange.presentation.auth.phone.PhoneActivity.Companion.EXTRA_IS_SUCCESS

class SmsAuthFactory(private val activity: Activity) {
    @JavascriptInterface
    fun resultAuth(
        isSuccess: Boolean,
        impUid: String?,
    ) {
        val intent = Intent()

        if (isSuccess && impUid != null) {
            intent.putExtra(EXTRA_IS_SUCCESS, true)
            intent.putExtra(EXTRA_IMP_UID, impUid)
            activity.setResult(RESULT_OK, intent)
            activity.finish()
        } else {
            intent.putExtra(EXTRA_IS_SUCCESS, false)
            activity.setResult(RESULT_OK, intent)
            activity.finish()
        }
    }
}
