package co.orange.presentation.bank

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import co.orange.core.base.BaseActivity
import co.orange.core.extension.colorOf
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.stringOf
import co.orange.core.extension.toast
import co.orange.presentation.delivery.AddressWebActivity
import co.orange.presentation.delivery.AddressWebBridge
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
        initAddressFindBtnListener()
        observeAddressResult()
    }

    private fun initBackBtnListener() {
        binding.btnBack.setOnSingleClickListener { finish() }
    }

    private fun initConfirmBtnListener() {
        val addressId = intent.getLongExtra(EXTRA_ACCOUNT_ID, -1)
        binding.btnConfirm.setOnSingleClickListener {
            if (addressId == DEFAULT_ID) {
                viewModel.postToAddAddressToServer()
            } else {
                viewModel.putToModAddressToServer(addressId)
            }
        }
    }

    private fun initAddressFindBtnListener() {
        with(binding) {
            btnFindZipcode.setOnSingleClickListener { navigateToAddressWebView() }
            btnFindAddress.setOnSingleClickListener { navigateToAddressWebView() }
        }
    }

    private fun navigateToAddressWebView() {
        AddressWebActivity.open { bundle ->
            setResultBundle(
                bundle.getString(AddressWebBridge.EXTRA_ZIPCODE),
                bundle.getString(
                    AddressWebBridge.EXTRA_ADDRESS,
                ),
            )
        }
    }

    private fun setResultBundle(
        resultZipCode: String?,
        resultAddress: String?,
    ) {
        with(viewModel) {
            zipCode = resultZipCode.orEmpty()
            address = resultAddress.orEmpty()
            checkIsCompleted()
        }
        with(binding) {
            tvAddressZipcode.text = resultZipCode
            tvAddressDelivery.text = resultAddress
            tvAddressDelivery.setTextColor(colorOf(R.color.gray_3))
        }
    }

    private fun observeAddressResult() {
        viewModel.setAddressResult.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { isSuccess ->
                if (isSuccess) {
                    toast(stringOf(R.string.address_toast))
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
