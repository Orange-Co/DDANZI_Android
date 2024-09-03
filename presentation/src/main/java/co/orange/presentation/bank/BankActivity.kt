package co.orange.presentation.bank

import android.os.Bundle
import androidx.activity.viewModels
import co.orange.core.base.BaseActivity
import co.orange.core.extension.setOnSingleClickListener
import co.orange.domain.entity.response.BankModel
import co.orange.presentation.bank.add.BankAddActivity
import co.orange.presentation.bank.add.BankAddActivity.Companion.DEFAULT_ID
import dagger.hilt.android.AndroidEntryPoint
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivityBankBinding

@AndroidEntryPoint
class BankActivity : BaseActivity<ActivityBankBinding>(R.layout.activity_bank) {
    val viewModel by viewModels<BankViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBackBtnListener()
        initBankInfoBtnListener()
        setDeliveryUi(viewModel.mockBankModel)
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

    private fun setDeliveryUi(item: BankModel) {
        with(binding) {
            tvBankName.text = item.bank
            tvBankAccount.text = item.accountNumber
            tvBankOwner.text = item.name
        }
    }
}
