package co.orange.presentation.setting

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import co.orange.core.base.BaseActivity
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.stringOf
import co.orange.core.extension.toast
import co.orange.core.state.UiState
import co.orange.presentation.setting.account.AccountActivity
import co.orange.presentation.setting.bank.BankActivity
import co.orange.presentation.setting.delivery.DeliveryActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivitySettingBinding

@AndroidEntryPoint
class SettingActivity : BaseActivity<ActivitySettingBinding>(R.layout.activity_setting) {
    val viewModel by viewModels<SettingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBackBtnListener()
        initDeliveryManageBtnListener()
        initBankManageBtnListener()
        initAccountManageBtnListener()
        observeGetSettingInfoState()
    }

    private fun initBackBtnListener() {
        binding.btnBack.setOnSingleClickListener { finish() }
    }

    private fun initDeliveryManageBtnListener() {
        binding.btnManageDelivery.setOnSingleClickListener {
            Intent(this, DeliveryActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun initBankManageBtnListener() {
        binding.btnManageBank.setOnSingleClickListener {
            Intent(this, BankActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun initAccountManageBtnListener() {
        binding.btnManageAccount.setOnSingleClickListener {
            Intent(this, AccountActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun observeGetSettingInfoState() {
        viewModel.getSettingInfoState.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { state ->
                when (state) {
                    is UiState.Success -> {
                        with(binding) {
                            tvSettingInfoName.text = state.data.name
                            tvSettingInfoPhone.text = state.data.phone
                            tvSettingInfoNickname.text = state.data.nickname
                        }
                    }

                    is UiState.Failure -> toast(stringOf(R.string.error_msg))
                    else -> return@onEach
                }
            }.launchIn(lifecycleScope)
    }
}
