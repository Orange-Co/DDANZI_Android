package co.orange.presentation.auth.signup_x

import android.os.Bundle
import androidx.activity.viewModels
import co.orange.core.base.BaseActivity
import co.orange.core.extension.hideKeyboard
import co.orange.core.extension.initFocusWithKeyboard
import co.orange.core.extension.setOnSingleClickListener
import co.orange.presentation.auth.phone.TermBottomSheet
import dagger.hilt.android.AndroidEntryPoint
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivitySignUpBinding

@AndroidEntryPoint
class SignUpActivity : BaseActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {
    private val viewModel by viewModels<SignUpViewModel>()

    private var mobileBottomSheet: MobileBottomSheet? = null
    private var termBottomSheet: TermBottomSheet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.vm = viewModel
        initConfirmBtnListener()
        initMobileBtnListener()
        initVerifyBtnListener()
        observeBirthFrontFinished()
        observeBirthBackFinished()
        observeMobileFinished()
        observePhoneFinished()
        observeCodeFinished()
    }

    private fun initConfirmBtnListener() {
        binding.btnConfirm.setOnSingleClickListener {
            termBottomSheet = TermBottomSheet()
            termBottomSheet?.show(supportFragmentManager, BOTTOM_SHEET_TERM)
        }
    }

    private fun initMobileBtnListener() {
        binding.btnSelectMobile.setOnSingleClickListener { showMobileBottomSheet() }
    }

    private fun showMobileBottomSheet() {
        mobileBottomSheet = MobileBottomSheet()
        mobileBottomSheet?.show(supportFragmentManager, BOTTOM_SHEET_MOBILE)
    }

    private fun initVerifyBtnListener() {
        binding.btnPhoneCertify.setOnSingleClickListener { }
    }

    private fun observeBirthFrontFinished() {
        viewModel.isBirthFrontFinished.observe(this) { isFinished ->
            if (isFinished) {
                binding.etSignUpBirthBack.requestFocus()
            }
        }
    }

    private fun observeBirthBackFinished() {
        viewModel.isBirthBackFinished.observe(this) { isFinished ->
            if (isFinished) {
                currentFocus?.let { hideKeyboard(it) }
                currentFocus?.clearFocus()
                showMobileBottomSheet()
            }
        }
    }

    private fun observeMobileFinished() {
        viewModel.isMobileFinished.observe(this) { isFinished ->
            if (isFinished) {
                initFocusWithKeyboard(binding.etSignUpPhone)
            }
        }
    }

    private fun observePhoneFinished() {
        viewModel.isPhoneFinished.observe(this) { isFinished ->
            if (isFinished) {
                currentFocus?.let { hideKeyboard(it) }
                currentFocus?.clearFocus()
            }
        }
    }

    private fun observeCodeFinished() {
        viewModel.isCodeFinished.observe(this) { isFinished ->
            if (isFinished) {
                currentFocus?.let { hideKeyboard(it) }
                currentFocus?.clearFocus()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        mobileBottomSheet = null
        termBottomSheet = null
    }

    companion object {
        private const val BOTTOM_SHEET_MOBILE = "BOTTOM_SHEET_MOBILE"
        private const val BOTTOM_SHEET_TERM = "BOTTOM_SHEET_TERM"
    }
}
