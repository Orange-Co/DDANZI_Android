package co.orange.presentation.auth.phone

import android.os.Bundle
import android.util.Log
import co.orange.core.base.BaseActivity
import co.orange.core.extension.setOnSingleClickListener
import com.google.gson.GsonBuilder
import com.iamport.sdk.data.sdk.IamPortCertification
import com.iamport.sdk.data.sdk.IamPortResponse
import com.iamport.sdk.domain.core.ICallbackPaymentResult
import com.iamport.sdk.domain.core.Iamport
import dagger.hilt.android.AndroidEntryPoint
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivityPhoneBinding
import timber.log.Timber

@AndroidEntryPoint
class PhoneActivity : BaseActivity<ActivityPhoneBinding>(R.layout.activity_phone) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAuthBtnListener()
    }

    private fun initAuthBtnListener() {
        binding.btnPhoneAuth.setOnSingleClickListener {
            Iamport.init(this)
            val userCode = "imp24882465"
            val certification =
                IamPortCertification(
                    merchant_uid = "MIIddanzi1",
                    company = "유어포트",
                )

            Iamport.certification(
                userCode,
                iamPortCertification = certification,
            ) { callBackListener.result(it) }
        }
    }

    private val callBackListener =
        object : ICallbackPaymentResult {
            override fun result(iamPortResponse: IamPortResponse?) {
                val resJson = GsonBuilder().setPrettyPrinting().create().toJson(iamPortResponse)
                Log.i("qqqq", "결제 결과 콜백\n$resJson")
                var result = iamPortResponse
                Timber.tag("qqqq").d(result.toString())
            }
        }
}
