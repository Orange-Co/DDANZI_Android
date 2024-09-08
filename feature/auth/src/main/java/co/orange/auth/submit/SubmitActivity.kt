package co.orange.auth.submit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import co.orange.auth.databinding.ActivitySubmitBinding
import co.orange.core.R
import co.orange.core.base.BaseActivity
import co.orange.core.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import co.orange.auth.R as featureR

@AndroidEntryPoint
class SubmitActivity : BaseActivity<ActivitySubmitBinding>(featureR.layout.activity_submit) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initConfirmBtnListener()
        getIntentUi()
    }

    private fun initConfirmBtnListener() {
        binding.btnConfirm.setOnSingleClickListener {
            finish()
        }
    }

    private fun getIntentUi() {
        binding.tvConfirmNickname.text =
            getString(R.string.sign_up_nickname, intent.getStringExtra(EXTRA_NICKNAME))
    }

    companion object {
        private const val EXTRA_NICKNAME = "EXTRA_NICKNAME"

        @JvmStatic
        fun createIntent(
            context: Context,
            nickname: String,
        ): Intent =
            Intent(context, SubmitActivity::class.java).apply {
                putExtra(EXTRA_NICKNAME, nickname)
            }
    }
}
