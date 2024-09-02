package co.orange.presentation.sell.progress

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.activityViewModels
import co.orange.core.base.BaseDialog
import co.orange.core.extension.setOnSingleClickListener
import co.orange.presentation.setting.bank.BankActivity
import kr.genti.presentation.R
import kr.genti.presentation.databinding.DialogBankBinding

class BankDialog :
    BaseDialog<DialogBankBinding>(R.layout.dialog_bank) {
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
