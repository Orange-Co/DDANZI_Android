package co.orange.presentation.auth.phone

import android.os.Bundle
import co.orange.core.base.BaseActivity
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.stringOf
import com.iamport.sdk.data.sdk.IamPortCertification
import com.iamport.sdk.domain.core.Iamport
import dagger.hilt.android.AndroidEntryPoint
import kr.genti.presentation.BuildConfig.IAMPORT_CODE
import kr.genti.presentation.BuildConfig.MERCHANT_UID
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivityPhoneBinding

@AndroidEntryPoint
class PhoneActivity : BaseActivity<ActivityPhoneBinding>(R.layout.activity_phone) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Iamport.init(this)
        initAuthBtnListener()
    }

    private fun initAuthBtnListener() {
        binding.btnPhoneAuth.setOnSingleClickListener {
            startIamportCertification()
        }
    }

    private fun startIamportCertification() {
        Iamport.certification(
            userCode = IAMPORT_CODE,
            iamPortCertification =
                IamPortCertification(
                    merchant_uid = MERCHANT_UID,
                    company = stringOf(R.string.company_name),
                ),
        ) { response ->
            // IamPortResponse(imp_success=null, success=true, imp_uid=imp_452864483577, merchant_uid=MIIddanzi1, error_msg=null, error_code=null)
            if (response != null && response.success == true) {
                response.imp_uid
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Iamport.close()
    }
}
