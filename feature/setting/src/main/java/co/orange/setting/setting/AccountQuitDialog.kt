package co.orange.setting.setting

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import co.orange.core.R
import co.orange.core.base.BaseDialog
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.stringOf
import co.orange.core.extension.toast
import co.orange.core.state.UiState
import co.orange.core.util.RestartUtil.restartApp
import co.orange.setting.databinding.DialogAccountQuitBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import co.orange.setting.R as featureR

class AccountQuitDialog :
    BaseDialog<DialogAccountQuitBinding>(featureR.layout.dialog_account_quit) {
    private val viewModel by activityViewModels<AccountViewModel>()

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
            )
            setBackgroundDrawableResource(R.color.transparent)
        }
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        initReturnBtnListener()
        initQuitBtnListener()
        observeKakaoQuitResult()
        observeUserQuitState()
    }

    private fun initReturnBtnListener() {
        binding.btnReturn.setOnSingleClickListener { dismiss() }
    }

    private fun initQuitBtnListener() {
        binding.btnQuit.setOnSingleClickListener {
            viewModel.quitKakaoAccount()
        }
    }

    private fun observeKakaoQuitResult() {
        viewModel.kakaoQuitResult.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { isSuccess ->
                if (!isSuccess) toast(stringOf(R.string.error_msg))
            }.launchIn(lifecycleScope)
    }

    private fun observeUserQuitState() {
        viewModel.userQuitState.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { state ->
                when (state) {
                    is UiState.Success -> {
                        delay(500)
                        restartApp(binding.root.context, null)
                    }

                    is UiState.Failure -> toast(stringOf(R.string.error_msg))
                    else -> return@onEach
                }
            }.launchIn(lifecycleScope)
    }
}
