package co.orange.presentation.auth.signup

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.genti.core.base.BaseActivity
import kr.genti.core.extension.hideKeyboard
import kr.genti.core.extension.initFocusWithKeyboard
import kr.genti.core.extension.setOnSingleClickListener
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivitySignUpBinding

@AndroidEntryPoint
class SignUpActivity : BaseActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {
    private val viewModel by viewModels<SignUpViewModel>()

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
//            Intent(this, MainActivity::class.java).apply {
//                startActivity(this)
//            }
//            finish()
        }
    }

    private fun initMobileBtnListener() {
        binding.btnSelectMobile.setOnSingleClickListener { showMobileBottomSheet() }
    }

    private fun showMobileBottomSheet() {
        // TODO
    }

    private fun initVerifyBtnListener() {
        binding.btnConfirm.setOnSingleClickListener {
            // TODO 서버통신
        }
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
}
