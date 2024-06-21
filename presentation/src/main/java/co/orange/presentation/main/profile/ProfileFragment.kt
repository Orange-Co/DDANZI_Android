package co.orange.presentation.main.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import co.orange.presentation.setting.SettingActivity
import dagger.hilt.android.AndroidEntryPoint
import kr.genti.core.base.BaseFragment
import kr.genti.core.extension.setOnSingleClickListener
import kr.genti.presentation.R
import kr.genti.presentation.databinding.FragmentProfileBinding

@AndroidEntryPoint
class ProfileFragment() : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
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
        if (false) {
            with(binding) {
                layoutYesLogin.isVisible = true
                layoutNotLogin.isVisible = false
                tvProfileInfoName.text = "김상호"
            }
        }
    }
}
