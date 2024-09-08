package co.orange.auth.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import co.orange.auth.databinding.ActivityLoginBinding
import co.orange.auth.phone.PhoneActivity
import co.orange.core.R
import co.orange.core.base.BaseActivity
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.stringOf
import co.orange.core.extension.toast
import co.orange.core.state.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import co.orange.auth.R as featureR

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(featureR.layout.activity_login) {
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLoginBtnListener()
        observeAppLoginAvailable()
        observeGetFCMTokenResult()
        observeChangeTokenResult()
    }

    private fun initLoginBtnListener() {
        binding.btnLoginKakao.setOnSingleClickListener {
            viewModel.startLogInWithKakao(this)
        }
    }

    private fun observeAppLoginAvailable() {
        viewModel.isAppLoginAvailable.flowWithLifecycle(lifecycle).onEach { isAvailable ->
            if (!isAvailable) viewModel.startLogInWithKakao(this)
        }.launchIn(lifecycleScope)
    }

    private fun observeGetFCMTokenResult() {
        viewModel.getFCMTokenResult.flowWithLifecycle(lifecycle).onEach { isSuccess ->
            if (!isSuccess) toast(stringOf(R.string.error_msg))
        }.launchIn(lifecycleScope)
    }

    private fun observeChangeTokenResult() {
        viewModel.changeTokenState.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { state ->
                when (state) {
                    is UiState.Success -> {
                        if (state.data != USER_ALREADY_SIGNED) {
                            startActivity(Intent(this, PhoneActivity::class.java))
                        }
                        finish()
                    }

                    is UiState.Failure -> toast(stringOf(R.string.error_msg))
                    else -> return@onEach
                }
            }.launchIn(lifecycleScope)
    }

    companion object {
        private const val USER_ALREADY_SIGNED = "ACTIVATE"
    }
}
