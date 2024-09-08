package co.orange.main.main.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import co.orange.core.R
import co.orange.core.base.BaseFragment
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.stringOf
import co.orange.core.extension.toast
import co.orange.core.navigation.NavigationManager
import co.orange.core.state.UiState
import co.orange.main.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import co.orange.main.R as featureR

@AndroidEntryPoint
class ProfileFragment() : BaseFragment<FragmentProfileBinding>(featureR.layout.fragment_profile) {
    @Inject
    lateinit var navigationManager: NavigationManager

    private val viewModel by activityViewModels<ProfileViewModel>()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        initSettingBtnListener()
        initLoginBtnListener()
        initQuestionBtnListener()
        initCenterBtnListener()
        initHistoryBtnsListener()
        observeGetNicknameState()
    }

    override fun onResume() {
        super.onResume()

        checkIsLogined()
    }

    private fun initSettingBtnListener() {
        binding.btnSetting.setOnSingleClickListener {
            navigationManager.toSettingView()
        }
    }

    private fun initLoginBtnListener() {
        binding.btnLogin.setOnSingleClickListener {
            navigationManager.toLoginView()
        }
    }

    private fun initQuestionBtnListener() {
        binding.btnCenterQuestion.setOnSingleClickListener {
            Intent(Intent.ACTION_VIEW, Uri.parse(WEB_QUESTION)).apply {
                startActivity(this)
            }
        }
    }

    private fun initCenterBtnListener() {
        binding.btnCenterOneOnOne.setOnSingleClickListener {
            Intent(requireContext(), ReportActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun initHistoryBtnsListener() {
        with(binding) {
            btnHistoryPurchase.setOnSingleClickListener { navigateToHistory(TYPE_BUY) }
            btnHistorySell.setOnSingleClickListener { navigateToHistory(TYPE_SELL) }
            btnHistoryLike.setOnSingleClickListener { navigateToHistory(TYPE_INTEREST) }
        }
    }

    private fun navigateToHistory(type: Int) {
        navigationManager.toHistoryView(type)
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

    companion object {
        const val TYPE_BUY = 0
        const val TYPE_SELL = 1
        const val TYPE_INTEREST = 2

        const val WEB_QUESTION =
            "https://brawny-guan-098.notion.site/0e7b016d88fb4b8ab066f45e644f365b?pvs=4"
    }
}
