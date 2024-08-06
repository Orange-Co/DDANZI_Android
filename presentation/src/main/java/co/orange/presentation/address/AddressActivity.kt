package co.orange.presentation.address

import android.os.Bundle
import co.orange.core.base.BaseActivity
import co.orange.core.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivityAddressBinding

@AndroidEntryPoint
class AddressActivity : BaseActivity<ActivityAddressBinding>(R.layout.activity_address) {
    override fun onStart() {
        super.onStart()
        AddressWebActivity.register(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAddressFindBtnListener()
    }

    private fun initAddressFindBtnListener() {
        with(binding) {
            btnFindZipcode.setOnSingleClickListener { navigateToAddressWebView() }
            btnFindAddress.setOnSingleClickListener { navigateToAddressWebView() }
        }
    }

    private fun navigateToAddressWebView() {
        AddressWebActivity.open { bundle ->
            val zipCode = bundle.getString(AddressWebBridge.ZIPCODE)
            val address = bundle.getString(AddressWebBridge.ADDRESS)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        AddressWebActivity.unregister()
    }
}
