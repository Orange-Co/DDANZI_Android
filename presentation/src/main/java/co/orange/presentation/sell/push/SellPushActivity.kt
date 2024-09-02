package co.orange.presentation.sell.push

import android.content.Context
import android.content.Intent
import android.os.Bundle
import co.orange.core.base.BaseActivity
import co.orange.core.extension.initOnBackPressedListener
import co.orange.core.extension.setOnSingleClickListener
import co.orange.presentation.sell.finished.SellFinishedActivity
import dagger.hilt.android.AndroidEntryPoint
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivityPushBinding

@AndroidEntryPoint
class SellPushActivity : BaseActivity<ActivityPushBinding>(R.layout.activity_push) {
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
        val productId = intent.getLongExtra(EXTRA_ITEM_ID, -1)
        SellFinishedActivity.createIntent(this, productId).apply {
            startActivity(this)
        }
    }

    companion object {
        private const val EXTRA_ITEM_ID = "EXTRA_ITEM_ID"

        @JvmStatic
        fun createIntent(
            context: Context,
            itemId: Long,
        ): Intent =
            Intent(context, SellPushActivity::class.java).apply {
                putExtra(EXTRA_ITEM_ID, itemId)
            }
    }
}
