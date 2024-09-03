package co.orange.presentation.bank

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import co.orange.core.base.BaseActivity
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.stringOf
import co.orange.core.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivityBankAddBinding

@AndroidEntryPoint
class AddBankActivity : BaseActivity<ActivityBankAddBinding>(R.layout.activity_bank_add) {
    val viewModel by viewModels<AddBankViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.vm = viewModel
        initBackBtnListener()
        initConfirmBtnListener()
        observeAddressResult()
    }

    private fun initBackBtnListener() {
        binding.btnBack.setOnSingleClickListener { finish() }
    }

    private fun initConfirmBtnListener() {
        val accountId = intent.getLongExtra(EXTRA_ACCOUNT_ID, -1)
        binding.btnConfirm.setOnSingleClickListener {
            if (accountId == DEFAULT_ID) {
                viewModel.postToAddBankToServer()
            } else {
                viewModel.putToModBankToServer(accountId)
            }
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

    companion object {
        private const val EXTRA_ACCOUNT_ID = "EXTRA_ACCOUNT_ID"
        const val DEFAULT_ID: Long = -1

        @JvmStatic
        fun createIntent(
            context: Context,
            accountId: Long,
        ): Intent =
            Intent(context, AddBankActivity::class.java).apply {
                putExtra(EXTRA_ACCOUNT_ID, accountId)
            }
    }
}
