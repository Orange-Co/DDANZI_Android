package co.orange.presentation.bank

import android.os.Bundle
import androidx.activity.viewModels
import co.orange.core.base.BaseActivity
import co.orange.core.extension.setOnSingleClickListener
import co.orange.domain.entity.response.AccountModel
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
        setDeliveryUi(viewModel.mockAccountModel)
    }

    private fun initBackBtnListener() {
        binding.btnBack.setOnSingleClickListener { finish() }
    }

    private fun initBankInfoBtnListener() {
        // TODO
        binding.layoutBankItem.setOnSingleClickListener { }
    }

    private fun setDeliveryUi(item: AccountModel) {
        with(binding) {
            tvBankName.text = item.bank
            tvBankAccount.text = item.accountNumber
            tvBankOwner.text = item.name
        }
    }
}
