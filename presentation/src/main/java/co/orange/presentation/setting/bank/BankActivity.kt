package co.orange.presentation.setting.bank

import android.os.Bundle
import androidx.activity.viewModels
import co.orange.domain.entity.response.AddressInfoModel
import kr.genti.core.base.BaseActivity
import kr.genti.core.extension.setOnSingleClickListener
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivityBankBinding

class BankActivityy : BaseActivity<ActivityBankBinding>(R.layout.activity_bank) {
    val viewModel by viewModels<BankViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBackBtnListener()
        initBankInfoBtnListener()
        setDeliveryUi(viewModel.mockAddressModel)
    }

    private fun initBackBtnListener() {
        binding.btnBack.setOnSingleClickListener { finish() }
    }

    private fun initBankInfoBtnListener() {
        // TODO
        binding.layoutBankItem.setOnSingleClickListener { }
    }

    private fun setDeliveryUi(item: AddressInfoModel) {
        with(binding) {
        }
    }
}
