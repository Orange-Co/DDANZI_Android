package co.orange.presentation.setting.delivery

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import co.orange.core.base.BaseActivity
import co.orange.core.extension.breakLines
import co.orange.core.extension.setOnSingleClickListener
import co.orange.domain.entity.response.AddressInfoModel
import co.orange.presentation.address.AddressActivity
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivityDeliveryBinding

class DeliveryActivity : BaseActivity<ActivityDeliveryBinding>(R.layout.activity_delivery) {
    val viewModel by viewModels<DeliveryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBackBtnListener()
        initWebBtnListener()
        initDeleteBtnListener()
        setDeliveryUi(viewModel.mockAddressModel)
    }

    private fun initBackBtnListener() {
        binding.btnBack.setOnSingleClickListener { finish() }
    }

    private fun initWebBtnListener() {
        with(binding) {
            btnDeliveryAdd.setOnSingleClickListener { navigateToAddressView() }
            btnDeliveryMod.setOnSingleClickListener { navigateToAddressView() }
        }
    }

    private fun navigateToAddressView() {
        Intent(this, AddressActivity::class.java).apply {
            startActivity(this)
        }
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
