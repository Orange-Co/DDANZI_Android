package co.orange.presentation.setting

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

    private var accountLogoutDialog: AccountLogoutDialog? = null
    private var accountQuitDialog: AccountQuitDialog? = null

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
        binding.btnQuit.setOnSingleClickListener {
            accountQuitDialog = AccountQuitDialog()
            accountQuitDialog?.show(supportFragmentManager, DIALOG_QUIT)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        accountLogoutDialog = null
        accountQuitDialog = null
    }

    companion object {
        private const val DIALOG_LOGOUT = "DIALOG_LOGOUT"
        private const val DIALOG_QUIT = "DIALOG_QUIT"
    }
}
