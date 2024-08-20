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
import co.orange.presentation.auth.login.LoginActivity
import co.orange.presentation.main.profile.history.HistoryActivity
import co.orange.presentation.main.profile.history.HistoryActivity.Companion.TYPE_BUY
import co.orange.presentation.main.profile.history.HistoryActivity.Companion.TYPE_INTEREST
import co.orange.presentation.main.profile.history.HistoryActivity.Companion.TYPE_SELL
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
        binding.btnLogin.setOnSingleClickListener {
            Intent(requireActivity(), LoginActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun initWebBtnsListener() {
        // TODO
        binding.btnBenefit.setOnSingleClickListener { }
        binding.btnCenterQuestion.setOnSingleClickListener { }
        binding.btnCenterOneOnOne.setOnSingleClickListener { }
    }

    private fun initHistoryBtnsListener() {
        with(binding) {
            btnHistoryPurchase.setOnSingleClickListener {
                HistoryActivity.createIntent(requireContext(), TYPE_BUY)
                    .apply { startActivity(this) }
            }
            btnHistorySell.setOnSingleClickListener {
                HistoryActivity.createIntent(requireContext(), TYPE_SELL)
                    .apply { startActivity(this) }
            }
            btnHistoryLike.setOnSingleClickListener {
                HistoryActivity.createIntent(requireContext(), TYPE_INTEREST)
                    .apply { startActivity(this) }
            }
        }
    }

    private fun checkIsLogined() {
        if (viewModel.getUserLogined()) {
            with(binding) {
                layoutYesLogin.isVisible = true
                binding.btnSetting.isVisible = true
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
