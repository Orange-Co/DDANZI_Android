package co.orange.setting.setting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import co.orange.core.R
import co.orange.core.base.BaseActivity
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.stringOf
import co.orange.core.extension.toast
import co.orange.core.state.UiState
import co.orange.setting.account.AccountActivity
import co.orange.setting.bank.BankActivity
import co.orange.setting.databinding.ActivitySettingBinding
import co.orange.setting.delivery.DeliveryActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import co.orange.setting.R as featureR

@AndroidEntryPoint
class SettingActivity : BaseActivity<ActivitySettingBinding>(featureR.layout.activity_setting) {
    val viewModel by viewModels<SettingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBackBtnListener()
        initDeliveryManageBtnListener()
        initBankManageBtnListener()
        initAccountManageBtnListener()
        initTermBtnsListener()
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

    private fun initTermBtnsListener() {
        with(binding) {
            btnTermPrivate.setOnSingleClickListener {
                Intent(Intent.ACTION_VIEW, Uri.parse(WEB_TERM_PRIVATE)).apply {
                    startActivity(this)
                }
            }
            btnTermService.setOnSingleClickListener {
                Intent(Intent.ACTION_VIEW, Uri.parse(WEB_TERM_SERVICE)).apply {
                    startActivity(this)
                }
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

    companion object {
        const val WEB_TERM_PRIVATE =
            "https://brawny-guan-098.notion.site/5a8b57e78f594988aaab08b8160c3072?pvs=4"
        const val WEB_TERM_SERVICE =
            "https://brawny-guan-098.notion.site/faa1517ffed44f6a88021a41407ed736?pvs=4"
    }
}
