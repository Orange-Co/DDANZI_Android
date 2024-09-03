package co.orange.presentation.sell.confirm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import co.orange.core.base.BaseActivity
import co.orange.core.extension.breakLines
import co.orange.core.extension.stringOf
import co.orange.core.extension.toast
import co.orange.core.state.UiState
import co.orange.domain.entity.response.SellBuyerInfoModel
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

        getIntentInfo()
        observeGetBuyerInfoState()
    }

    private fun getIntentInfo() {
        with(viewModel) {
            orderId = "082201407240828017AUC"
            getBuyerInfoFromServer()
        }
    }

    private fun observeGetBuyerInfoState() {
        viewModel.getBuyerInfoState.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { state ->
                when (state) {
                    is UiState.Success -> setIntentUi(state.data)
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
        }
    }

    companion object {
        private const val EXTRA_ORDER_ID = "EXTRA_ORDER_ID"

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
