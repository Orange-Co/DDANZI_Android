package co.orange.presentation.main.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import co.orange.core.base.BaseFragment
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.stringOf
import co.orange.core.extension.toast
import co.orange.core.state.UiState
import co.orange.presentation.setting.SettingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kr.genti.presentation.R
import kr.genti.presentation.databinding.FragmentProfileBinding

@AndroidEntryPoint
class ProfileFragment() : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    private val viewModel by activityViewModels<ProfileViewModel>()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        initSettingBtnListener()
        initLoginBtnListener()
        initWebBtnsListener()
        initHistoryBtnsListener()
        checkIsLogined()
        observeGetNicknameState()
    }

    private fun initSettingBtnListener() {
        binding.btnSetting.setOnSingleClickListener {
            Intent(requireContext(), SettingActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun initLoginBtnListener() {
        // TODO
        binding.btnLogin.setOnSingleClickListener { }
    }

    private fun initWebBtnsListener() {
        // TODO
        binding.btnBenefit.setOnSingleClickListener { }
        binding.btnCenterQuestion.setOnSingleClickListener { }
        binding.btnCenterOneOnOne.setOnSingleClickListener { }
    }

    private fun initHistoryBtnsListener() {
        // TODO
        binding.btnHistoryPurchase.setOnSingleClickListener { }
        binding.btnHistorySell.setOnSingleClickListener { }
        binding.btnHistoryLike.setOnSingleClickListener { }
    }

    private fun checkIsLogined() {
        // TODO 추후 토큰 반영
        if (true) {
            with(binding) {
                layoutYesLogin.isVisible = true
                layoutNotLogin.isVisible = false
            }
            viewModel.getNicknameFromServer()
        }
    }

    private fun observeGetNicknameState() {
        viewModel.getNicknameState.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { state ->
                when (state) {
                    is UiState.Success -> binding.tvProfileInfoName.text = state.data.nickname
                    is UiState.Failure -> toast(stringOf(R.string.error_msg))
                    else -> return@onEach
                }
            }.launchIn(lifecycleScope)
    }
}
