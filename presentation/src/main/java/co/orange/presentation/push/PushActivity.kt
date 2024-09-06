package co.orange.presentation.push

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import co.orange.core.base.BaseActivity
import co.orange.core.extension.initOnBackPressedListener
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.stringOf
import co.orange.core.extension.toast
import co.orange.presentation.buy.finished.BuyFinishedActivity
import co.orange.presentation.sell.finished.SellFinishedActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivityPushBinding

@AndroidEntryPoint
class PushActivity : BaseActivity<ActivityPushBinding>(R.layout.activity_push) {
    private val viewModel by viewModels<PushViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initOnBackPressedListener(binding.root)
        initExitBtnListener()
        initAlarmBtnListener()
        observeGetDeviceTokenResult()
    }

    private fun initExitBtnListener() {
        with(binding) {
            btnExit.setOnSingleClickListener {
                checkIsBuyOrSell()
            }
            btnLater.setOnSingleClickListener {
                checkIsBuyOrSell()
            }
        }
    }

    private fun initAlarmBtnListener() {
        binding.btnAlarm.setOnSingleClickListener {
            // TODO 푸시알람
        }
    }

    private fun checkIsBuyOrSell() {
        if (intent.getBooleanExtra(EXTRA_IS_BUYING, true)) {
            navigateToBuyFinishedActivity()
        } else {
            navigateToSellFinishedActivity()
        }
    }

    private fun navigateToBuyFinishedActivity() {
        BuyFinishedActivity.createIntent(
            this,
            intent.getStringExtra(EXTRA_ORDER_ID).orEmpty(),
        ).apply { startActivity(this) }
    }

    private fun navigateToSellFinishedActivity() {
        SellFinishedActivity.createIntent(
            this,
            intent.getStringExtra(EXTRA_ITEM_ID).orEmpty(),
            intent.getStringExtra(EXTRA_PRODUCT_NAME).orEmpty(),
            intent.getStringExtra(EXTRA_PRODUCT_IMAGE).orEmpty(),
            intent.getIntExtra(EXTRA_SALE_PRICE, 0),
        ).apply { startActivity(this) }
    }

    private fun observeGetDeviceTokenResult() {
        viewModel.getDeviceTokenResult.flowWithLifecycle(lifecycle).onEach { isSuccess ->
            if (!isSuccess) toast(stringOf(R.string.error_msg))
        }.launchIn(lifecycleScope)
    }

    companion object {
        private const val EXTRA_IS_BUYING = "EXTRA_IS_BUYING"
        private const val EXTRA_ORDER_ID = "EXTRA_ORDER_ID"
        private const val EXTRA_ITEM_ID = "EXTRA_ITEM_ID"
        private const val EXTRA_PRODUCT_NAME = "EXTRA_PRODUCT_NAME"
        private const val EXTRA_PRODUCT_IMAGE = "EXTRA_PRODUCT_IMAGE"
        private const val EXTRA_SALE_PRICE = "EXTRA_SALE_PRICE"

        @JvmStatic
        fun createIntent(
            context: Context,
            isBuying: Boolean,
            orderId: String? = null,
            itemId: String? = null,
            productName: String? = null,
            productImage: String? = null,
            salePrice: Int? = null,
        ): Intent =
            Intent(context, PushActivity::class.java).apply {
                putExtra(EXTRA_IS_BUYING, isBuying)
                putExtra(EXTRA_ORDER_ID, orderId)
                putExtra(EXTRA_ITEM_ID, itemId)
                putExtra(EXTRA_PRODUCT_NAME, productName)
                putExtra(EXTRA_PRODUCT_IMAGE, productImage)
                putExtra(EXTRA_SALE_PRICE, salePrice)
            }
    }
}
