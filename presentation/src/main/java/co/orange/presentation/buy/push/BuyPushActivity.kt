package co.orange.presentation.buy.push

import android.content.Context
import android.content.Intent
import android.os.Bundle
import co.orange.core.base.BaseActivity
import co.orange.core.extension.initOnBackPressedListener
import co.orange.core.extension.setOnSingleClickListener
import co.orange.presentation.buy.finished.BuyFinishedActivity
import dagger.hilt.android.AndroidEntryPoint
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivityPushBinding

@AndroidEntryPoint
class BuyPushActivity : BaseActivity<ActivityPushBinding>(R.layout.activity_push) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initOnBackPressedListener(binding.root)
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
        val orderId = intent.getStringExtra(EXTRA_ORDER_ID).orEmpty()
        BuyFinishedActivity.createIntent(this, orderId).apply {
            startActivity(this)
        }
    }

    companion object {
        private const val EXTRA_ORDER_ID = "EXTRA_ORDER_ID"

        @JvmStatic
        fun createIntent(
            context: Context,
            productId: String,
        ): Intent =
            Intent(context, BuyPushActivity::class.java).apply {
                putExtra(EXTRA_ORDER_ID, productId)
            }
    }
}
