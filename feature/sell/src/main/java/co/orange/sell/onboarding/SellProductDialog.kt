package co.orange.sell.onboarding

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.activityViewModels
import co.orange.core.R
import co.orange.core.base.BaseDialog
import co.orange.core.extension.setOnSingleClickListener
import co.orange.sell.databinding.DialogSellProductBinding
import co.orange.sell.progress.SellProgressActivity
import coil.load
import co.orange.sell.R as featureR

class SellProductDialog :
    BaseDialog<DialogSellProductBinding>(featureR.layout.dialog_sell_product) {
    private val viewModel by activityViewModels<SellOnboardingViewModel>()

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
            )
            setBackgroundDrawableResource(R.color.transparent)
        }
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        initExitBtnListener()
        initConfirmBtnListener()
        initPickAgainBtnListener()
        setSellProductUi()
    }

    private fun initExitBtnListener() {
        binding.btnExit.setOnSingleClickListener { dismiss() }
    }

    private fun initConfirmBtnListener() {
        binding.btnSubmit.setOnSingleClickListener {
            SellProgressActivity.createIntent(
                requireContext(),
                viewModel.productId,
                viewModel.productName,
                viewModel.uploadedUrl,
            ).apply { startActivity(this) }
            dismiss()
        }
    }

    private fun initPickAgainBtnListener() {
        binding.btnPickAgain.setOnSingleClickListener {
            viewModel.setCheckedAgain(true)
            dismiss()
        }
    }

    private fun setSellProductUi() {
        with(binding) {
            ivSellProduct.load(viewModel.productImage)
            tvSellProductName.text = viewModel.productName
        }
    }
}
