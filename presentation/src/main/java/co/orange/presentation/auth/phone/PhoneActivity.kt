package co.orange.presentation.auth.phone

import android.content.Intent
import android.os.Bundle
import co.orange.core.base.BaseActivity
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivityPhoneBinding

@AndroidEntryPoint
class PhoneActivity : BaseActivity<ActivityPhoneBinding>(R.layout.activity_phone) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAuthBtnListener()
    }

    private fun initAuthBtnListener() {
        binding.btnPhoneAuth.setOnSingleClickListener {
            SmsAuthActivity.createIntent(this).apply {
                startActivityForResult(this, SMS_AUTH_REQ_CODE)
            }
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SMS_AUTH_REQ_CODE) {
            data?.let {
                toast("${it.getStringExtra(EXTRA_IMP_UID)}")
            }
        }
    }

    companion object {
        const val SMS_AUTH_REQ_CODE = 202

        const val EXTRA_IS_SUCCESS = "EXTRA_IS_SUCCESS"
        const val EXTRA_IMP_UID = "EXTRA_IMP_UID"
    }
}
