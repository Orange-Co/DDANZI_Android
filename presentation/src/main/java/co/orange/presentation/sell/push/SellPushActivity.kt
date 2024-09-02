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
        SellFinishedActivity.createIntent(
            this,
            intent.getStringExtra(EXTRA_ITEM_ID).orEmpty(),
            intent.getStringExtra(EXTRA_PRODUCT_NAME).orEmpty(),
            intent.getStringExtra(EXTRA_PRODUCT_IMAGE).orEmpty(),
            intent.getIntExtra(EXTRA_SALE_PRICE, 0),
        ).apply { startActivity(this) }
    }

    companion object {
        private const val EXTRA_ITEM_ID = "EXTRA_ITEM_ID"
        private const val EXTRA_PRODUCT_NAME = "EXTRA_PRODUCT_NAME"
        private const val EXTRA_PRODUCT_IMAGE = "EXTRA_PRODUCT_IMAGE"
        private const val EXTRA_SALE_PRICE = "EXTRA_SALE_PRICE"

        @JvmStatic
        fun createIntent(
            context: Context,
            itemId: String,
            productName: String,
            productImage: String,
            salePrice: Int,
        ): Intent =
            Intent(context, SellPushActivity::class.java).apply {
                putExtra(EXTRA_ITEM_ID, itemId)
                putExtra(EXTRA_PRODUCT_NAME, productName)
                putExtra(EXTRA_PRODUCT_IMAGE, productImage)
                putExtra(EXTRA_SALE_PRICE, salePrice)
            }
    }
}
