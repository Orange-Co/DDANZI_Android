package co.orange.presentation.auth.phone

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import co.orange.core.base.BaseActivity
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.setStatusBarColorFromResource
import co.orange.core.extension.stringOf
import co.orange.core.extension.toast
import co.orange.core.state.UiState
import co.orange.presentation.auth.submit.SubmitActivity
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
import timber.log.Timber

@AndroidEntryPoint
class PhoneActivity : BaseActivity<ActivityPhoneBinding>(R.layout.activity_phone) {
    private val viewModel by viewModels<PhoneViewModel>()

    private var termBottomSheet: TermBottomSheet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Iamport.init(this)
        initAuthBtnListener()
        observeIsSubmitClicked()
        observeIamportTokenResult()
        observeIamportCertificationResult()
        observeSignUpState()
    }

    private fun initAuthBtnListener() {
        binding.btnPhoneAuth.setOnSingleClickListener {
            termBottomSheet = TermBottomSheet()
            termBottomSheet?.show(supportFragmentManager, BOTTOM_SHEET_TERM)
        }
    }

    private fun observeIsSubmitClicked() {
        viewModel.isSubmitClicked.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { isSuccess ->
                if (isSuccess) {
                    setLoadingScreen(true)
                    viewModel.clickSubmitBtn(false)
                    startIamportCertification()
                }
            }.launchIn(lifecycleScope)
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
            Timber.tag("okhttp").d("IAMPORT CERTIFICATION RESPONSE : $response")
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
                if (!isSuccess) {
                    toast(stringOf(R.string.error_msg))
                    setLoadingScreen(false)
                }
            }.launchIn(lifecycleScope)
    }

    private fun observeIamportCertificationResult() {
        viewModel.getIamportCertificationResult.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { isSuccess ->
                if (!isSuccess) {
                    toast(stringOf(R.string.error_msg))
                    setLoadingScreen(false)
                }
            }.launchIn(lifecycleScope)
    }

    private fun observeSignUpState() {
        viewModel.postSignUpState.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { state ->
                when (state) {
                    is UiState.Success -> {
                        setLoadingScreen(false)
                        SubmitActivity.createIntent(this, state.data).apply {
                            startActivity(this)
                        }
                        finish()
                    }

                    is UiState.Failure -> {
                        toast(stringOf(R.string.error_msg))
                        setLoadingScreen(false)
                    }

                    else -> return@onEach
                }
            }.launchIn(lifecycleScope)
    }

    private fun setLoadingScreen(isLoading: Boolean) {
        if (isLoading) {
            setStatusBarColorFromResource(R.color.background_50)
            binding.layoutLoading.isVisible = true
        } else {
            setStatusBarColorFromResource(R.color.white)
            binding.layoutLoading.isVisible = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Iamport.close()
        termBottomSheet = null
    }

    companion object {
        private const val BOTTOM_SHEET_TERM = "BOTTOM_SHEET_TERM"
    }
}
