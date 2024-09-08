package co.orange.presentation.delivery

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import co.orange.core.R
import co.orange.core.base.BaseActivity
import co.orange.core.extension.breakLines
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.stringOf
import co.orange.core.extension.toast
import co.orange.core.state.UiState
import co.orange.presentation.delivery.address.AddressActivity
import co.orange.presentation.delivery.address.AddressActivity.Companion.DEFAULT_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kr.genti.presentation.databinding.ActivityDeliveryBinding
import kr.genti.presentation.R as featureR

@AndroidEntryPoint
class DeliveryActivity : BaseActivity<ActivityDeliveryBinding>(featureR.layout.activity_delivery) {
    val viewModel by viewModels<DeliveryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBackBtnListener()
        initAddressBtnListener()
        initDeleteBtnListener()
        observeUserAddressState()
        observeDeleteAddressResult()
    }

    override fun onResume() {
        super.onResume()

        viewModel.getUserAddressFromServer()
    }

    private fun initBackBtnListener() {
        binding.btnBack.setOnSingleClickListener { finish() }
    }

    private fun initAddressBtnListener() {
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
        binding.btnDeliveryDelete.setOnSingleClickListener {
            viewModel.deleteUserAddressFromServer()
        }
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
                                tvDeliveryName.text = state.data.recipient
                                tvDeliveryAddress.text =
                                    getString(
                                        R.string.address_format,
                                        state.data.zipCode,
                                        state.data.address,
                                        state.data.detailAddress,
                                    ).breakLines()
                                tvDeliveryPhone.text = state.data.recipientPhone
                            }
                        }
                    }

                    is UiState.Failure -> toast(stringOf(R.string.error_msg))
                    else -> return@onEach
                }
            }.launchIn(lifecycleScope)
    }

    private fun observeDeleteAddressResult() {
        viewModel.deleteAddressResult.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { isSuccess ->
                if (isSuccess) {
                    toast(stringOf(R.string.address_delete_toast))
                    with(binding) {
                        btnDeliveryAdd.isVisible = true
                        layoutDeliveryItem.isVisible = false
                    }
                } else {
                    toast(stringOf(R.string.error_msg))
                }
            }.launchIn(lifecycleScope)
    }
}
