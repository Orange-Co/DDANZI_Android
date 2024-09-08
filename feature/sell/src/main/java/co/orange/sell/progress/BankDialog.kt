package co.orange.sell.progress

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.activityViewModels
import co.orange.core.R
import co.orange.core.base.BaseDialog
import co.orange.core.extension.setOnSingleClickListener
import co.orange.presentation.bank.BankActivity
import co.orange.sell.databinding.DialogBankBinding
import co.orange.sell.R as featureR

class BankDialog :
    BaseDialog<DialogBankBinding>(featureR.layout.dialog_bank) {
    private val viewModel by activityViewModels<SellProgressViewModel>()

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
            )
            setBackgroundDrawableResource(R.color.transparent)
        }
        dialog?.setOnCancelListener {
            viewModel.setLoadingState(false)
        }
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        initSubmitBtnListener()
    }

    private fun initSubmitBtnListener() {
        binding.btnSubmit.setOnSingleClickListener {
            viewModel.isSentToBank = true
            Intent(requireContext(), BankActivity::class.java).apply {
                startActivity(this)
            }
            dismiss()
        }
    }
}
