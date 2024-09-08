package co.orange.presentation.bank.type

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import co.orange.core.R
import co.orange.core.base.BaseBottomSheet
import co.orange.domain.enums.BankType
import co.orange.presentation.bank.add.BankAddViewModel
import kr.genti.presentation.databinding.BottomSheetBankTypeBinding
import kr.genti.presentation.R as featureR

class BankTypeBottomSheet :
    BaseBottomSheet<BottomSheetBankTypeBinding>(featureR.layout.bottom_sheet_bank_type) {
    private val viewModel by activityViewModels<BankAddViewModel>()

    private var _adapter: BankTypeAdapter? = null
    val adapter
        get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(R.color.transparent)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        initBankTypeAdapter()
        setBankType()
    }

    private fun initBankTypeAdapter() {
        _adapter =
            BankTypeAdapter(
                itemClick = ::initBankTypeClickListener,
            )
        binding.rvBankType.adapter = adapter
    }

    private fun initBankTypeClickListener(item: BankType) {
        with(viewModel) {
            bankName.value = item.displayName
            bankCode = item.code
            isBankSelected.value = true
        }
        dismiss()
    }

    private fun setBankType() {
        adapter.submitList(BankType.values().toList())
    }
}
