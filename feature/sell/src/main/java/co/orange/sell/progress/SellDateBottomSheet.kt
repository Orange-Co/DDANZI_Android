package co.orange.sell.progress

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import co.orange.core.R
import co.orange.core.base.BaseBottomSheet
import co.orange.core.extension.setOnSingleClickListener
import co.orange.sell.databinding.BottomSheetSellDateBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import co.orange.sell.R as featureR

class SellDateBottomSheet :
    BaseBottomSheet<BottomSheetSellDateBinding>(featureR.layout.bottom_sheet_sell_date) {
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
        binding.btnSubmit.setOnSingleClickListener {
            with(viewModel) {
                sellDate.value = getDateFromDatePicker()
                isDateSelected.value = true
            }
            dismiss()
        }
    }

    private fun getDateFromDatePicker(): String =
        with(binding.dpSell) {
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
                Calendar.getInstance().apply {
                    set(year, month, dayOfMonth)
                }.time,
            )
        }

    private fun setDatePicker() {
        binding.dpSell.apply {
            val calendar = Calendar.getInstance()
            minDate = calendar.timeInMillis
            calendar.add(Calendar.DAY_OF_YEAR, 7)
            maxDate = calendar.timeInMillis
        }
    }
}
