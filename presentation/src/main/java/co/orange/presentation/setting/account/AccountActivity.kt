package co.orange.presentation.setting.account

import android.os.Bundle
import androidx.activity.viewModels
import co.orange.core.base.BaseActivity
import co.orange.core.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivityAccountBinding

@AndroidEntryPoint
class AccountActivity : BaseActivity<ActivityAccountBinding>(R.layout.activity_account) {
    val viewModel by viewModels<AccountViewModel>()

    var accountLogoutDialog: AccountLogoutDialog? = null

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
        binding.btnLogout.setOnSingleClickListener {
            accountLogoutDialog = AccountLogoutDialog()
            accountLogoutDialog?.show(supportFragmentManager, DIALOG_LOGOUT)
        }
    }

    private fun initQuitBtnListener() {
        // TODO
        binding.btnQuit.setOnSingleClickListener { }
    }

    override fun onDestroy() {
        super.onDestroy()
        accountLogoutDialog = null
    }

    companion object {
        private const val DIALOG_LOGOUT = "DIALOG_LOGOUT"
    }
}
