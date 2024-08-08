package co.orange.presentation.address

import android.content.Context
import android.content.Intent
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
        initConfirmBtnListener()
        initAddressFindBtnListener()
    }

    private fun initBackBtnListener() {
        binding.btnBack.setOnSingleClickListener { finish() }
    }

    private fun initConfirmBtnListener() {
        binding.btnConfirm.setOnSingleClickListener {
            if (intent.getBooleanExtra(EXTRA_IS_FIRST, true)) {
                // 추가 서버통신
            } else {
                // 변경 서버통신
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

    override fun onDestroy() {
        super.onDestroy()
        AddressWebActivity.unregister()
    }

    companion object {
        private const val EXTRA_IS_FIRST = "EXTRA_IS_FIRST"

        @JvmStatic
        fun createIntent(
            context: Context,
            isFirst: Boolean,
        ): Intent =
            Intent(context, AddressActivity::class.java).apply {
                putExtra(EXTRA_IS_FIRST, isFirst)
            }
    }
}
