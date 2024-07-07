package co.orange.presentation.auth.submit

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import kr.genti.core.base.BaseActivity
import kr.genti.core.extension.setOnSingleClickListener
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivitySubmitBinding

@AndroidEntryPoint
class SubmitActivity : BaseActivity<ActivitySubmitBinding>(R.layout.activity_submit) {
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
        binding.tvConfirmSubtitle.text = "행복한물개13123님"
    }
}
