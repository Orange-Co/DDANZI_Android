package co.orange.presentation.address

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
import co.orange.presentation.address.AddressWebBridge.Companion.EXTRA_ADDRESS
import co.orange.presentation.address.AddressWebBridge.Companion.EXTRA_ZIPCODE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivityAddressBinding

@AndroidEntryPoint
class AddressActivity : BaseActivity<ActivityAddressBinding>(R.layout.activity_address) {
    val viewModel by viewModels<AddressViewModel>()

    override fun onStart() {
        super.onStart()
        AddressWebActivity.register(this)
    }

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
        val addressId = intent.getLongExtra(EXTRA_ADDRESS_ID, -1)
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
            setResultBundle(bundle.getString(EXTRA_ZIPCODE), bundle.getString(EXTRA_ADDRESS))
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

    override fun onDestroy() {
        super.onDestroy()
        AddressWebActivity.unregister()
    }

    companion object {
        private const val EXTRA_ADDRESS_ID = "EXTRA_ADDRESS_ID"
        const val DEFAULT_ID: Long = -1

        @JvmStatic
        fun createIntent(
            context: Context,
            addressId: Long,
        ): Intent =
            Intent(context, AddressActivity::class.java).apply {
                putExtra(EXTRA_ADDRESS_ID, addressId)
            }
    }
}
