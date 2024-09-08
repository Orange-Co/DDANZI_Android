package co.orange.sell.confirm

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import co.orange.core.R
import co.orange.core.base.BaseActivity
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.stringOf
import co.orange.core.extension.toast
import co.orange.core.navigation.NavigationManager
import co.orange.core.state.UiState
import co.orange.domain.entity.response.SellBuyerInfoModel
import co.orange.sell.databinding.ActivitySellConfirmBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import co.orange.sell.R as featureR

@AndroidEntryPoint
class SellConfirmActivity :
    BaseActivity<ActivitySellConfirmBinding>(featureR.layout.activity_sell_confirm) {
    @Inject
    lateinit var navigationManager: NavigationManager

    private val viewModel by viewModels<SellConfirmViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBackBtnListener()
        initGuideBtnListener()
        initConfirmBtnListener()
        getIntentInfo()
        observeGetBuyerInfoState()
        observePatchOrderConfirmResult()
    }

    private fun initBackBtnListener() {
        binding.btnBack.setOnSingleClickListener { finish() }
    }

    private fun initGuideBtnListener() {
        binding.btnSellGuide.setOnSingleClickListener {
            Intent(Intent.ACTION_VIEW, Uri.parse(WEB_TERM_SELL)).apply {
                startActivity(this)
            }
        }
    }

    private fun initConfirmBtnListener() {
        binding.btnConfirm.setOnSingleClickListener {
            viewModel.patchOrderConfirmToServer()
        }
    }

    private fun getIntentInfo() {
        with(viewModel) {
            orderId = intent.getStringExtra(EXTRA_ORDER_ID).orEmpty()
            getBuyerInfoFromServer()
        }
    }

    private fun observeGetBuyerInfoState() {
        viewModel.getBuyerInfoState.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { state ->
                when (state) {
                    is UiState.Success -> {
                        setIntentUi(state.data)
                        setCopyBtnListener(state.data)
                    }

                    is UiState.Failure -> toast(stringOf(R.string.error_msg))
                    else -> return@onEach
                }
            }.launchIn(lifecycleScope)
    }

    private fun setIntentUi(item: SellBuyerInfoModel) {
        with(binding) {
            tvSellConfirmAddress.text = item.address
            tvSellConfirmAddressDetail.text = item.detailAddress
            tvSellConfirmName.text = item.recipient
            tvSellConfirmPhone.text = item.recipientPhone
            item.selectedOptionList.takeIf { it.isNotEmpty() }?.let { optionItem ->
                tvSellConfirmOption.text =
                    optionItem.joinToString(separator = "\n") { "${it.option}: ${it.selectedOption}" }
            } ?: run {
                tvSellConfirmOptionTitle.isVisible = false
                tvSellConfirmOption.isVisible = false
            }
        }
    }

    private fun setCopyBtnListener(item: SellBuyerInfoModel) {
        with(binding) {
            btnCopyAddress.setOnSingleClickListener { copyText(item.address) }
            btnCopyAddressDetail.setOnSingleClickListener { copyText(item.detailAddress) }
            btnCopyName.setOnSingleClickListener { copyText(item.recipient) }
            btnCopyPhone.setOnSingleClickListener { copyText(item.recipientPhone) }
        }
    }

    private fun copyText(text: String) {
        val clipboardManager = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.setPrimaryClip(ClipData.newPlainText(CLIP_LABEL, text))
    }

    private fun observePatchOrderConfirmResult() {
        viewModel.patchOrderConfirmResult.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { isSuccess ->
                if (isSuccess) {
                    toast(stringOf(R.string.sell_order_fix_msg))
                    navigationManager.toMainViewWIthClearing(this)
                } else {
                    toast(stringOf(R.string.error_msg))
                }
            }.launchIn(lifecycleScope)
    }

    companion object {
        private const val EXTRA_ORDER_ID = "EXTRA_ORDER_ID"
        private const val CLIP_LABEL = "BUYER_INFO"

        const val WEB_TERM_SELL =
            "https://brawny-guan-098.notion.site/6d77260d027148ceb0f806f0911c284a?pvs=4"

        @JvmStatic
        fun createIntent(
            context: Context,
            orderId: String,
        ): Intent =
            Intent(context, SellConfirmActivity::class.java).apply {
                putExtra(EXTRA_ORDER_ID, orderId)
            }
    }
}
