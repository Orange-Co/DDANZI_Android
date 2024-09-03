package co.orange.presentation.sell.confirm

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import co.orange.core.base.BaseActivity
import co.orange.core.extension.breakLines
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.stringOf
import co.orange.core.extension.toast
import co.orange.core.state.UiState
import co.orange.domain.entity.response.SellBuyerInfoModel
import co.orange.presentation.setting.SettingActivity.Companion.WEB_TERM_SELL
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivitySellConfirmBinding

@AndroidEntryPoint
class SellConfirmActivity :
    BaseActivity<ActivitySellConfirmBinding>(R.layout.activity_sell_confirm) {
    private val viewModel by viewModels<SellConfirmViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBackBtnListener()
        initGuideBtnListener()
        getIntentInfo()
        observeGetBuyerInfoState()
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
        binding.btnConfirm.setOnSingleClickListener { }
    }

    private fun getIntentInfo() {
        with(viewModel) {
            orderId = intent.getStringExtra(EXTRA_ORDER_ID).orEmpty()
            // orderId = "082201407240828017AUC"
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
            tvSellConfirmAddress.text =
                getString(
                    R.string.address_short_format,
                    item.zipCode,
                    item.address,
                ).breakLines()
            tvSellConfirmAddressDetail.text = item.detailAddress
            tvSellConfirmName.text = item.recipient
            tvSellConfirmPhone.text = item.recipientPhone
            tvSellConfirmOption.text = item.selectedOptionList.joinToString(separator = "\n")
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

    companion object {
        private const val EXTRA_ORDER_ID = "EXTRA_ORDER_ID"
        private const val CLIP_LABEL = "BUYER_INFO"

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
