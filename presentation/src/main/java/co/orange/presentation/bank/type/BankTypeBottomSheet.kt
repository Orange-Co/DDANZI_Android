package co.orange.presentation.bank.type

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import co.orange.core.base.BaseBottomSheet
import co.orange.presentation.bank.add.BankAddViewModel
import kr.genti.presentation.R
import kr.genti.presentation.databinding.BottomSheetBankTypeBinding

class BankTypeBottomSheet :
    BaseBottomSheet<BottomSheetBankTypeBinding>(R.layout.bottom_sheet_bank_type) {
    private val viewModel by activityViewModels<BankAddViewModel>()

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(R.color.transparent)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
    }
}
