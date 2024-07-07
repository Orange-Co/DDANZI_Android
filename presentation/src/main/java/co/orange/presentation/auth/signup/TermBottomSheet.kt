package co.orange.presentation.auth.signup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import kr.genti.core.base.BaseBottomSheet
import kr.genti.core.extension.setOnSingleClickListener
import kr.genti.presentation.R
import kr.genti.presentation.databinding.BottomSheetTermBinding

class TermBottomSheet :
    BaseBottomSheet<BottomSheetTermBinding>(R.layout.bottom_sheet_term) {
    private val viewModel by activityViewModels<SignUpViewModel>()

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
//            Intent(this, MainActivity::class.java).apply {
//                startActivity(this)
//            }
//            finish()
        }
    }
}
