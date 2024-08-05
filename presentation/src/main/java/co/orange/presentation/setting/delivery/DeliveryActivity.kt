package co.orange.presentation.setting.delivery

import android.os.Bundle
import androidx.activity.viewModels
import co.orange.core.base.BaseActivity
import co.orange.core.extension.breakLines
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.toast
import co.orange.domain.entity.response.AddressInfoModel
import co.orange.presentation.setting.delivery.AddressBridge.Companion.ADDRESS
import co.orange.presentation.setting.delivery.AddressBridge.Companion.ZIPCODE
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivityDeliveryBinding

class DeliveryActivity : BaseActivity<ActivityDeliveryBinding>(R.layout.activity_delivery) {
    val viewModel by viewModels<DeliveryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBackBtnListener()
        initAddBtnListener()
        initModBtnListener()
        initDeleteBtnListener()
        setDeliveryUi(viewModel.mockAddressModel)
    }

    override fun onStart() {
        super.onStart()
        AddressActivity.register(this)
    }

    private fun initBackBtnListener() {
        binding.btnBack.setOnSingleClickListener { finish() }
    }

    private fun initAddBtnListener() {
        binding.btnAddDelivery.setOnSingleClickListener {
            AddressActivity.open { bundle ->
                val zipCode = bundle.getString(ZIPCODE)
                val address = bundle.getString(ADDRESS)
                if (zipCode?.isNotEmpty() == true && address?.isNotEmpty() == true) {
                    toast("주소: [$zipCode] $address")
                }
            }
        }
    }

    private fun initModBtnListener() {
        // TODO
        binding.btnDeliveryMod.setOnSingleClickListener { }
    }

    private fun initDeleteBtnListener() {
        // TODO
        binding.btnDeliveryDelete.setOnSingleClickListener { }
    }

    private fun setDeliveryUi(item: AddressInfoModel) {
        with(binding) {
            tvDeliveryName.text = item.recipient
            tvDeliveryAddress.text =
                getString(
                    R.string.address_format,
                    item.zipCode,
                    item.address,
                ).breakLines()
            tvDeliveryPhone.text = item.phone
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        AddressActivity.unregister()
    }
}
