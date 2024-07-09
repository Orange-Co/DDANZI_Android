package co.orange.presentation.setting.account

import android.os.Bundle
import androidx.activity.viewModels
import co.orange.core.base.BaseActivity
import co.orange.core.extension.setOnSingleClickListener
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivityAccountBinding

class AccountActivity : BaseActivity<ActivityAccountBinding>(R.layout.activity_account) {
    val viewModel by viewModels<AccountViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBackBtnListener()
        initLogoutBtnListener()
        initQuitBtnListener()
    }

    private fun initBackBtnListener() {
        binding.btnBack.setOnSingleClickListener { finish() }
    }

    private fun initLogoutBtnListener() {
        // TODO
        binding.btnLogout.setOnSingleClickListener { }
    }

    private fun initQuitBtnListener() {
        // TODO
        binding.btnQuit.setOnSingleClickListener { }
    }
}
