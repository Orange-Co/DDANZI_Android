package co.orange.presentation.setting.history

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import co.orange.core.base.BaseActivity
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.stringOf
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivityHistoryBinding

class HistoryActivity : BaseActivity<ActivityHistoryBinding>(R.layout.activity_history) {
    val viewModel by viewModels<HistoryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBackBtnListener()
        getTypeFromIntent()
    }

    private fun initBackBtnListener() {
        binding.btnBack.setOnSingleClickListener { finish() }
    }

    private fun getTypeFromIntent() {
        viewModel.currentType = intent.getIntExtra(EXTRA_TYPE, -1)
        setUiWithType()
    }

    private fun setUiWithType() {
        when (viewModel.currentType) {
            TYPE_BUY -> {
                binding.tvHistoryTitle.text = stringOf(R.string.profile_history_buy_title)
            }

            TYPE_SELL -> {
                binding.tvHistoryTitle.text = stringOf(R.string.profile_history_sell_title)
            }

            TYPE_INTEREST -> {
                binding.tvHistoryTitle.text = stringOf(R.string.profile_history_interest_title)
            }

            else -> return
        }
    }

    companion object {
        private const val EXTRA_TYPE = "EXTRA_TYPE"

        const val TYPE_BUY = 0
        const val TYPE_SELL = 1
        const val TYPE_INTEREST = 2

        @JvmStatic
        fun createIntent(
            context: Context,
            type: Int,
        ): Intent =
            Intent(context, HistoryActivity::class.java).apply {
                putExtra(EXTRA_TYPE, type)
            }
    }
}
