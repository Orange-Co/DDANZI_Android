package co.orange.presentation.setting

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import kr.genti.core.base.BaseActivity
import kr.genti.core.extension.setOnSingleClickListener
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivitySettingBinding

@AndroidEntryPoint
class SettingActivity : BaseActivity<ActivitySettingBinding>(R.layout.activity_setting) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBackBtnListener()
        initManageBtnsListener()
    }

    private fun initBackBtnListener() {
        binding.btnBack.setOnSingleClickListener { finish() }
    }

    private fun initManageBtnsListener() {
        // TODO
        binding.btnManageDelivery.setOnSingleClickListener { }
        binding.btnManageBank.setOnSingleClickListener { }
        binding.btnManageAccount.setOnSingleClickListener { }
    }
}
