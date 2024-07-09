package co.orange.presentation.setting.delivery

import android.os.Bundle
import androidx.activity.viewModels
import co.orange.core.base.BaseActivity
import co.orange.core.extension.breakLines
import co.orange.core.extension.setOnSingleClickListener
import co.orange.domain.entity.response.AddressInfoModel
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivityDeliveryBinding

class DeliveryActivity : BaseActivity<ActivityDeliveryBinding>(R.layout.activity_delivery) {
    val viewModel by viewModels<DeliveryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBackBtnListener()
        initModBtnListener()
        initDeleteBtnListener()
        setDeliveryUi(viewModel.mockAddressModel)
    }

    private fun initBackBtnListener() {
        binding.btnBack.setOnSingleClickListener { finish() }
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
}
