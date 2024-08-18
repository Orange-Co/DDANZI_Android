package co.orange.presentation.buy.progress

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import co.orange.core.base.BaseActivity
import co.orange.core.extension.setNumberForm
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.stringOf
import co.orange.core.extension.toast
import co.orange.core.state.UiState
import co.orange.domain.entity.response.BuyProgressModel
import co.orange.presentation.buy.push.BuyPushActivity
import co.orange.presentation.setting.delivery.DeliveryActivity
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivityBuyProgressBinding

@AndroidEntryPoint
class BuyProgressActivity :
    BaseActivity<ActivityBuyProgressBinding>(R.layout.activity_buy_progress) {
    private val viewModel by viewModels<BuyProgressViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initExitBtnListener()
        initDeliveryChangeBtnListener()
        initTermBtnListener()
        initConfirmBtnListener()
        observeGetBuyProgressDataState()
    }

    override fun onResume() {
        super.onResume()

        getIntentInfo()
    }

    private fun initExitBtnListener() {
        binding.btnExit.setOnSingleClickListener { finish() }
    }

    private fun initDeliveryChangeBtnListener() {
        binding.btnChangeDelivery.setOnSingleClickListener {
            Intent(this, DeliveryActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun initTermBtnListener() {
        // TODO
        binding.btnTermAll.setOnSingleClickListener { }
        binding.btnTermService.setOnSingleClickListener { }
        binding.btnTermPurchase.setOnSingleClickListener { }
    }

    private fun initConfirmBtnListener() {
        binding.btnConfirmPurchase.setOnSingleClickListener {
            // TODO 구매 요청 서버통신 이후
            BuyPushActivity.createIntent(this, viewModel.productId).apply {
                startActivity(this)
            }
        }
    }

    private fun getIntentInfo() {
        with(viewModel) {
            if (productId.isEmpty()) productId = intent.getStringExtra(EXTRA_PRODUCT_ID).orEmpty()
            getBuyProgressDataFromServer()
        }
    }

    private fun observeGetBuyProgressDataState() {
        viewModel.getBuyProgressDataState.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { state ->
                when (state) {
                    is UiState.Success -> setBuyProgressUi(state.data)

                    is UiState.Failure -> {
                        toast(stringOf(R.string.error_msg))
                        finish()
                    }

                    else -> return@onEach
                }
            }.launchIn(lifecycleScope)
    }

    private fun setBuyProgressUi(item: BuyProgressModel) {
        with(binding) {
            tvConfirmProductName.text = item.productName
            ivConfirmProduct.load(item.imgUrl)
            tvConfirmProductPrice.text = item.originPrice.setNumberForm()
            tvConfirmPriceMoney.text = item.originPrice.setNumberForm()
            tvConfirmPriceDiscount.text =
                getString(R.string.add_minus, item.discountPrice.setNumberForm())
            tvConfirmPriceCharge.text =
                getString(R.string.add_plus, item.charge.setNumberForm())
            tvConfirmPriceTotal.text = item.totalPrice.setNumberForm()
        }
    }

    companion object {
        private const val EXTRA_PRODUCT_ID = "EXTRA_PRODUCT_ID"

        @JvmStatic
        fun createIntent(
            context: Context,
            productId: String,
        ): Intent =
            Intent(context, BuyProgressActivity::class.java).apply {
                putExtra(EXTRA_PRODUCT_ID, productId)
            }
    }
}
