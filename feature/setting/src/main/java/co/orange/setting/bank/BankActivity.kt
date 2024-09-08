package co.orange.setting.bank

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import co.orange.core.R
import co.orange.core.base.BaseActivity
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.stringOf
import co.orange.core.extension.toast
import co.orange.core.state.UiState
import co.orange.domain.entity.response.BankModel
import co.orange.domain.enums.BankType
import co.orange.setting.bank.add.BankAddActivity
import co.orange.setting.bank.add.BankAddActivity.Companion.DEFAULT_ID
import co.orange.setting.databinding.ActivityBankBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import co.orange.setting.R as featureR

@AndroidEntryPoint
class BankActivity : BaseActivity<ActivityBankBinding>(featureR.layout.activity_bank) {
    val viewModel by viewModels<BankViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBackBtnListener()
        initBankInfoBtnListener()
        observeUserBankState()
    }

    override fun onResume() {
        super.onResume()

        viewModel.getUserBankFromServer()
    }

    private fun initBackBtnListener() {
        binding.btnBack.setOnSingleClickListener { finish() }
    }

    private fun initBankInfoBtnListener() {
        with(binding) {
            btnBankAdd.setOnSingleClickListener { navigateToAddBankView(DEFAULT_ID) }
            btnBankMod.setOnSingleClickListener { navigateToAddBankView(viewModel.accountId) }
        }
    }

    private fun navigateToAddBankView(accountId: Long) {
        BankAddActivity.createIntent(this, accountId).apply {
            startActivity(this)
        }
    }

    private fun observeUserBankState() {
        viewModel.getUserBankState.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { state ->
                when (state) {
                    is UiState.Success -> setDeliveryUi(state.data)
                    is UiState.Failure -> toast(stringOf(R.string.error_msg))
                    else -> return@onEach
                }
            }.launchIn(lifecycleScope)
    }

    private fun setDeliveryUi(item: BankModel) {
        with(binding) {
            btnBankAdd.isVisible = item.accountId == null
            btnBankMod.isVisible = item.accountId != null
            if (item.accountId != null) {
                tvBankName.text = item.bank?.let { BankType.fromCode(it) }
                tvBankAccount.text = item.accountNumber
                tvBankOwner.text = item.name
            }
        }
    }
}
