package co.orange.setting.bank.add

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import co.orange.core.R
import co.orange.core.amplitude.AmplitudeManager
import co.orange.core.base.BaseActivity
import co.orange.core.extension.maskName
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.stringOf
import co.orange.core.extension.toast
import co.orange.setting.bank.type.BankTypeBottomSheet
import co.orange.setting.databinding.ActivityBankAddBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import co.orange.setting.R as featureR

@AndroidEntryPoint
class BankAddActivity : BaseActivity<ActivityBankAddBinding>(featureR.layout.activity_bank_add) {
    val viewModel by viewModels<BankAddViewModel>()

    private var bankTypeBottomSheet: BankTypeBottomSheet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.vm = viewModel
        initBackBtnListener()
        initBankTypeListener()
        initConfirmBtnListener()
        getIntentFromPreviousView()
        observeAddressResult()
    }

    private fun initBackBtnListener() {
        binding.btnBack.setOnSingleClickListener { finish() }
    }

    private fun initBankTypeListener() {
        binding.btnAddBankName.setOnSingleClickListener {
            bankTypeBottomSheet = BankTypeBottomSheet()
            bankTypeBottomSheet?.show(supportFragmentManager, BOTTOM_SHEET_BANK_TYPE)
        }
    }

    private fun initConfirmBtnListener() {
        binding.btnConfirm.setOnSingleClickListener {
            AmplitudeManager.trackEvent("click_account_next")
            with(viewModel) {
                if (accountId == DEFAULT_ID) {
                    postToAddBankToServer()
                } else {
                    putToModBankToServer(accountId)
                }
            }
        }
    }

    private fun getIntentFromPreviousView() {
        with(viewModel) {
            accountId = intent.getLongExtra(EXTRA_ACCOUNT_ID, -1)
            ownerName = intent.getStringExtra(EXTRA_OWNER_NAME).orEmpty()
            maskedName.value = ownerName.takeIf { it.isNotEmpty() }?.maskName()
        }
    }

    private fun observeAddressResult() {
        viewModel.setBankResult.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { isSuccess ->
                if (isSuccess) {
                    toast(stringOf(R.string.bank_toast))
                    finish()
                } else {
                    toast(stringOf(R.string.error_msg))
                }
            }.launchIn(lifecycleScope)
    }

    override fun onDestroy() {
        super.onDestroy()

        bankTypeBottomSheet = null
    }

    companion object {
        const val DEFAULT_ID: Long = -1
        private const val BOTTOM_SHEET_BANK_TYPE = "BOTTOM_SHEET_BANK_TYPE"

        private const val EXTRA_ACCOUNT_ID = "EXTRA_ACCOUNT_ID"
        private const val EXTRA_OWNER_NAME = "EXTRA_OWNER_NAME"

        @JvmStatic
        fun createIntent(
            context: Context,
            accountId: Long,
            ownerName: String,
        ): Intent =
            Intent(context, BankAddActivity::class.java).apply {
                putExtra(EXTRA_ACCOUNT_ID, accountId)
                putExtra(EXTRA_OWNER_NAME, ownerName)
            }
    }
}
