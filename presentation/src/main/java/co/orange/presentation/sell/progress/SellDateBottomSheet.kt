package co.orange.presentation.sell.progress

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import co.orange.core.base.BaseBottomSheet
import co.orange.core.extension.setOnSingleClickListener
import kr.genti.presentation.R
import kr.genti.presentation.databinding.BottomSheetSellDateBinding
import java.util.Calendar

class SellDateBottomSheet :
    BaseBottomSheet<BottomSheetSellDateBinding>(R.layout.bottom_sheet_sell_date) {
    private val viewModel by activityViewModels<SellProgressViewModel>()

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(R.color.transparent)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        initSubmitBtnListener()
        setDatePicker()
    }

    private fun initSubmitBtnListener() {
        binding.btnSubmit.setOnSingleClickListener { }
    }

    private fun setDatePicker() {
        binding.dpSell.apply {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, -7)
            minDate = calendar.timeInMillis
            calendar.add(Calendar.DAY_OF_YEAR, 14)
            maxDate = calendar.timeInMillis
        }
    }
}
