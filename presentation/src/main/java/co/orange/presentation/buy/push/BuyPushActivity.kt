package co.orange.presentation.buy.push

import android.content.Context
import android.content.Intent
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import kr.genti.core.base.BaseActivity
import kr.genti.core.extension.setOnSingleClickListener
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivityPushBinding

@AndroidEntryPoint
class BuyPushActivity : BaseActivity<ActivityPushBinding>(R.layout.activity_push) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: 뒤로가기 방지 추가
        initExitBtnListener()
        initAlarmBtnListener()
    }

    private fun initExitBtnListener() {
        with(binding) {
            btnExit.setOnSingleClickListener {
                navigateToFinishedActivity()
            }
            btnLater.setOnSingleClickListener {
                navigateToFinishedActivity()
            }
        }
    }

    private fun initAlarmBtnListener() {
        binding.btnAlarm.setOnSingleClickListener {
            // TODO 푸시알람
            navigateToFinishedActivity()
        }
    }

    private fun navigateToFinishedActivity() {
        val imageUrl = intent.getStringExtra(EXTRA_PRODUCT_URL).orEmpty()
        val name = intent.getStringExtra(EXTRA_NAME).orEmpty()

    }

    companion object {
        private const val EXTRA_PRODUCT_URL = "EXTRA_PRODUCT_URL"
        private const val EXTRA_NAME = "EXTRA_NAME"

        @JvmStatic
        fun createIntent(
            context: Context,
            productUrl: String,
            name: String
        ): Intent = Intent(context, BuyPushActivity::class.java).apply {
            putExtra(EXTRA_PRODUCT_URL, productUrl)
            putExtra(EXTRA_NAME, name)
        }
    }
}