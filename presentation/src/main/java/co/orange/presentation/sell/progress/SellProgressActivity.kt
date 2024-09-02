package co.orange.presentation.sell.progress

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import co.orange.core.base.BaseActivity
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.setPriceForm
import co.orange.core.extension.setStatusBarColorFromResource
import co.orange.core.extension.stringOf
import co.orange.core.extension.toast
import co.orange.core.state.UiState
import co.orange.domain.entity.response.SellProductModel
import co.orange.presentation.setting.SettingActivity.Companion.WEB_TERM_SELL
import co.orange.presentation.setting.SettingActivity.Companion.WEB_TERM_SERVICE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivitySellProgressBinding

@AndroidEntryPoint
class SellProgressActivity :
    BaseActivity<ActivitySellProgressBinding>(R.layout.activity_sell_progress) {
    private val viewModel by viewModels<SellProgressViewModel>()

    private var sellDateBottomSheet: SellDateBottomSheet? = null
    private var bankDialog: BankDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initExitBtnListener()
        initTermDetailBtnListener()
        initConfirmBtnListener()
        initDateBtnListener()
        observeGetProductState()
    }

    override fun onResume() {
        super.onResume()
        getProductWithIdFromIntent()
    }

    private fun initExitBtnListener() {
        binding.btnExit.setOnSingleClickListener { finish() }
    }

    private fun initTermDetailBtnListener() {
        with(binding) {
            btnTermServiceDetail.setOnSingleClickListener {
                Intent(Intent.ACTION_VIEW, Uri.parse(WEB_TERM_SERVICE)).apply {
                    startActivity(this)
                }
            }
            btnTermSellDetail.setOnSingleClickListener {
                Intent(Intent.ACTION_VIEW, Uri.parse(WEB_TERM_SELL)).apply {
                    startActivity(this)
                }
            }
        }
    }

    private fun initConfirmBtnListener() {
        binding.btnConfirmSell.setOnSingleClickListener {
            setLoadingScreen(true)
            if (viewModel.isBankExist) {
                // TODO 뷰모델 구매 요청
//                SellPushActivity.createIntent(this, -1).apply {
//                    startActivity(this)
//                }
            } else {
                viewModel.isSentToBank = true
                bankDialog = BankDialog()
                bankDialog?.show(supportFragmentManager, DIALOG_BANK)
            }
        }
    }

    private fun initDateBtnListener() {
        with(binding) {
            btnSellDate.setOnSingleClickListener { startSelectingDate() }
            tvSellDate.setOnSingleClickListener { startSelectingDate() }
        }
    }

    private fun startSelectingDate() {
        sellDateBottomSheet = SellDateBottomSheet()
        sellDateBottomSheet?.show(supportFragmentManager, BOTTOM_SHEET_DATE)
    }

    private fun getProductWithIdFromIntent() {
        with(viewModel) {
            intent.getStringExtra(EXTRA_PRODUCT_ID)?.let { productId = it }
            getProductWIthId()
        }
    }

    private fun observeGetProductState() {
        viewModel.getProductState.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { state ->
                when (state) {
                    is UiState.Success -> {
                        setIntentUi(state.data)
                        // TODO 뷰모델 구매 요청
                    }

                    is UiState.Failure -> {
                        toast(stringOf(R.string.error_msg))
                        finish()
                    }

                    else -> return@onEach
                }
                setLoadingScreen(false)
            }.launchIn(lifecycleScope)
    }

    private fun setIntentUi(item: SellProductModel) {
        with(binding) {
            tvSellInfoName.text = item.productName
            tvSellInfoOriginPrice.text = item.originPrice.setPriceForm()
            tvSellInfoSellPrice.text = item.salePrice.setPriceForm()
            // TODO
            // ivSellProduct.load(R.drawable.mock_img_product)
        }
    }

    private fun setLoadingScreen(isLoading: Boolean) {
        if (isLoading) {
            setStatusBarColorFromResource(R.color.background_50)
            binding.layoutLoading.isVisible = true
        } else {
            setStatusBarColorFromResource(R.color.white)
            binding.layoutLoading.isVisible = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sellDateBottomSheet = null
        bankDialog = null
    }

    companion object {
        private const val BOTTOM_SHEET_DATE = "BOTTOM_SHEET_DATE"
        private const val DIALOG_BANK = " DIALOG_BANK"
        private const val EXTRA_PRODUCT_ID = "EXTRA_PRODUCT_ID"

        @JvmStatic
        fun createIntent(
            context: Context,
            productId: String,
        ): Intent =
            Intent(context, SellProgressActivity::class.java).apply {
                putExtra(EXTRA_PRODUCT_ID, productId)
            }
    }
}
