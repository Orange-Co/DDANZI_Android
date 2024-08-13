package co.orange.presentation.auth.phone

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import co.orange.core.base.BaseBottomSheet
import co.orange.core.extension.setOnSingleClickListener
import kr.genti.presentation.R
import kr.genti.presentation.databinding.BottomSheetTermBinding

class TermBottomSheet :
    BaseBottomSheet<BottomSheetTermBinding>(R.layout.bottom_sheet_term) {
    private val viewModel by activityViewModels<PhoneViewModel>()

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(R.color.transparent)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        initTermDetailBtnsListener()
        initSubmitBtnListener()
    }

    private fun initTermDetailBtnsListener() {
        with(binding) {
            // TODO
        }
    }

    private fun initSubmitBtnListener() {
        binding.btnSubmit.setOnSingleClickListener {
            viewModel.clickSubmitBtn()
            dismiss()
        }
    }
}
