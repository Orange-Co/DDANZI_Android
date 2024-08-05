package co.orange.presentation.setting.delivery

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import co.orange.core.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivityAddressBinding

@AndroidEntryPoint
class AddressActivity : BaseActivity<ActivityAddressBinding>(R.layout.activity_address) {
    private var launcher: ActivityResultLauncher<Bundle>? = null
    private val contract: ActivityResultContract<Bundle, Bundle>
        get() =
            object : ActivityResultContract<Bundle, Bundle>() {
                override fun createIntent(
                    context: Context,
                    input: Bundle,
                ): Intent = Intent("co.orange.presentation.setting.delivery.FINDER")

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setWebView()
    }

    private fun setWebView() {
        binding.webDaumAddress.apply {
            with(settings) {
                javaScriptEnabled = true
                allowFileAccessFromFileURLs = false
                allowUniversalAccessFromFileURLs = false
                allowFileAccess = false
                allowContentAccess = false
            }
            // addJavascriptInterface(JavascriptInterface(this@AddressFinder), JS_BRIDGE)
        }
    }

    fun open(onComplete: (Bundle) -> Unit) {
        action = onComplete
        launcher?.launch(Bundle())
    }

    fun register(activity: ComponentActivity) {
        launcher = activity.registerForActivityResult(contract) { b -> action?.invoke(b) }
    }

    fun unregister() {
        action = null
        launcher = null
    }

    companion object {
        private const val JS_BRIDGE = "address_finder"
        private const val DOMAIN = "address.finder.net" // 로컬 가상 도메인
        private const val PATH = "assets"

        const val ADDRESS = "address"
        const val ZIPCODE = "zipcode"
    }
}
