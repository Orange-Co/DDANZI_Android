package co.orange.sell.progress

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.activityViewModels
import co.orange.core.R
import co.orange.core.base.BaseDialog
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.navigation.NavigationManager
import co.orange.sell.databinding.DialogBankBinding
import javax.inject.Inject
import co.orange.sell.R as featureR

class BankDialog :
    BaseDialog<DialogBankBinding>(featureR.layout.dialog_bank) {
    @Inject
    lateinit var navigationManager: NavigationManager

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
            navigationManager.toBankView(requireContext())
            dismiss()
        }
    }
}
