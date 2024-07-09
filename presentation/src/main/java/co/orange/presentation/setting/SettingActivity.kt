package co.orange.presentation.setting

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import co.orange.core.base.BaseActivity
import co.orange.core.extension.setOnSingleClickListener
import co.orange.domain.entity.response.SettingModel
import co.orange.presentation.setting.account.AccountActivity
import co.orange.presentation.setting.bank.BankActivity
import co.orange.presentation.setting.delivery.DeliveryActivity
import dagger.hilt.android.AndroidEntryPoint
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
        setSettingInfo(viewModel.mockSettingModel)
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

    private fun setSettingInfo(item: SettingModel) {
        with(binding) {
            tvSettingInfoName.text = item.name
            tvSettingInfoPhone.text = item.phone
            tvSettingInfoNickname.text = item.nickname
        }
    }
}
