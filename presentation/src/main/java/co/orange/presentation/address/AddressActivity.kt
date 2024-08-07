package co.orange.presentation.address

import android.os.Bundle
import androidx.activity.viewModels
import co.orange.core.base.BaseActivity
import co.orange.core.extension.colorOf
import co.orange.core.extension.setOnSingleClickListener
import co.orange.presentation.address.AddressWebBridge.Companion.EXTRA_ADDRESS
import co.orange.presentation.address.AddressWebBridge.Companion.EXTRA_ZIPCODE
import dagger.hilt.android.AndroidEntryPoint
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
        initAddressFindBtnListener()
    }

    private fun initBackBtnListener() {
        binding.btnBack.setOnSingleClickListener { finish() }
    }

    private fun initConfirmBtnListener() {
        // TODO 서버통신
        binding.btnConfirm.setOnSingleClickListener {
            finish()
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

    override fun onDestroy() {
        super.onDestroy()
        AddressWebActivity.unregister()
    }
}
