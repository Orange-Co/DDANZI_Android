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
        val productId = intent.getStringExtra(EXTRA_PRODUCT_ID).orEmpty()

    }

    companion object {
        private const val EXTRA_PRODUCT_ID = "EXTRA_PRODUCT_ID"

        @JvmStatic
        fun createIntent(
            context: Context,
            productId: Long,
        ): Intent = Intent(context, BuyPushActivity::class.java).apply {
            putExtra(EXTRA_PRODUCT_ID, productId)
        }
    }
}