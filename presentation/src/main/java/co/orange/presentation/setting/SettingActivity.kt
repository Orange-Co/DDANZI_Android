package co.orange.presentation.setting

import android.os.Bundle
import androidx.activity.viewModels
import co.orange.domain.entity.response.SettingModel
import dagger.hilt.android.AndroidEntryPoint
import kr.genti.core.base.BaseActivity
import kr.genti.core.extension.setOnSingleClickListener
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivitySettingBinding

@AndroidEntryPoint
class SettingActivity : BaseActivity<ActivitySettingBinding>(R.layout.activity_setting) {
    val viewModel by viewModels<SettingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBackBtnListener()
        initManageBtnsListener()
        setSettingInfo(viewModel.mockSettingModel)
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

    private fun setSettingInfo(item: SettingModel) {
        with(binding) {
            tvSettingInfoName.text = item.name
            tvSettingInfoPhone.text = item.phone
            tvSettingInfoNickname.text = item.nickname
        }
    }
}
