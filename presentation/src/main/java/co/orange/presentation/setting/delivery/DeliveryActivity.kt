package co.orange.presentation.setting.delivery

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import co.orange.core.base.BaseActivity
import co.orange.core.extension.breakLines
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.stringOf
import co.orange.core.extension.toast
import co.orange.core.state.UiState
import co.orange.presentation.address.AddressActivity
import co.orange.presentation.address.AddressActivity.Companion.DEFAULT_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivityDeliveryBinding

@AndroidEntryPoint
class DeliveryActivity : BaseActivity<ActivityDeliveryBinding>(R.layout.activity_delivery) {
    val viewModel by viewModels<DeliveryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBackBtnListener()
        initWebBtnListener()
        initDeleteBtnListener()
        observeUserAddressState()
    }

    private fun initBackBtnListener() {
        binding.btnBack.setOnSingleClickListener { finish() }
    }

    private fun initWebBtnListener() {
        with(binding) {
            btnDeliveryAdd.setOnSingleClickListener { navigateToAddressView(DEFAULT_ID) }
            btnDeliveryMod.setOnSingleClickListener { navigateToAddressView(viewModel.addressId) }
        }
    }

    private fun navigateToAddressView(addressId: Long) {
        AddressActivity.createIntent(this, addressId).apply {
            startActivity(this)
        }
    }

    private fun initDeleteBtnListener() {
        // TODO
        binding.btnDeliveryDelete.setOnSingleClickListener { }
    }

    private fun observeUserAddressState() {
        viewModel.getUserAddressState.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { state ->
                when (state) {
                    is UiState.Success -> {
                        with(binding) {
                            if (state.data.addressId != null) {
                                btnDeliveryAdd.isVisible = false
                                layoutDeliveryItem.isVisible = true
                                tvDeliveryName.text = state.data.name
                                tvDeliveryAddress.text =
                                    getString(
                                        R.string.address_format,
                                        state.data.zipCode,
                                        state.data.address,
                                        state.data.detailAddress,
                                    ).breakLines()
                                tvDeliveryPhone.text = state.data.phone
                            }
                        }
                    }

                    is UiState.Failure -> toast(stringOf(R.string.error_msg))
                    else -> return@onEach
                }
            }.launchIn(lifecycleScope)
    }
}
