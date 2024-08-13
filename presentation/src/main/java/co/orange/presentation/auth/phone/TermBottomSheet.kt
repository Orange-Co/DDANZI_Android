package co.orange.presentation.auth.phone

import android.content.Intent
import android.os.Bundle
import android.view.View
import co.orange.core.base.BaseBottomSheet
import co.orange.core.extension.setOnSingleClickListener
import co.orange.presentation.auth.submit.SubmitActivity
import kr.genti.presentation.R
import kr.genti.presentation.databinding.BottomSheetTermBinding

class TermBottomSheet :
    BaseBottomSheet<BottomSheetTermBinding>(R.layout.bottom_sheet_term) {
    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(R.color.transparent)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        initTermBtnsListener()
        initSubmitBtnListener()
    }

    private fun initTermBtnsListener() {
        with(binding) {
            // TODO
        }
    }

    private fun initSubmitBtnListener() {
        binding.btnSubmit.setOnSingleClickListener {
            Intent(requireActivity(), SubmitActivity::class.java).apply {
                startActivity(this)
            }
            dismiss()
        }
    }
}
