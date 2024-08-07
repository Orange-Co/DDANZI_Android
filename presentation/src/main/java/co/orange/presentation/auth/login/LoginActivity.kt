package co.orange.presentation.auth.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import co.orange.core.base.BaseActivity
import co.orange.core.extension.setOnSingleClickListener
import co.orange.presentation.auth.phone.PhoneActivity
import dagger.hilt.android.AndroidEntryPoint
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivityLoginBinding

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLoginBtnListener()
    }

    private fun initLoginBtnListener() {
        binding.btnLoginKakao.setOnSingleClickListener {
            Intent(this, PhoneActivity::class.java).apply {
                startActivity(this)
            }
            finish()
        }
    }
}
