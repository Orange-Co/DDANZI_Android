package co.orange.presentation.auth.phone

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import co.orange.core.base.BaseActivity
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.stringOf
import co.orange.core.extension.toast
import com.iamport.sdk.data.sdk.IamPortCertification
import com.iamport.sdk.domain.core.Iamport
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kr.genti.presentation.BuildConfig.IAMPORT_CODE
import kr.genti.presentation.BuildConfig.MERCHANT_UID
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivityPhoneBinding

@AndroidEntryPoint
class PhoneActivity : BaseActivity<ActivityPhoneBinding>(R.layout.activity_phone) {
    private val viewModel by viewModels<PhoneViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Iamport.init(this)
        initAuthBtnListener()
        observeIamportTokenResult()
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
                with(viewModel) {
                    certificatedUid = response.imp_uid.toString()
                    postToGetIamportTokenFromServer()
                }
            }
        }
    }

    private fun observeIamportTokenResult() {
        viewModel.getIamportTokenResult.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { isSuccess ->
                if (!isSuccess) toast(stringOf(R.string.error_msg))
            }.launchIn(lifecycleScope)
    }

    override fun onDestroy() {
        super.onDestroy()
        Iamport.close()
    }
}
