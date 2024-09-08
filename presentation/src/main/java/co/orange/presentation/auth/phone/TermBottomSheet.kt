package co.orange.presentation.auth.phone

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import co.orange.core.R
import co.orange.core.base.BaseBottomSheet
import co.orange.core.extension.setOnSingleClickListener
import co.orange.presentation.setting.SettingActivity.Companion.WEB_TERM_PRIVATE
import co.orange.presentation.setting.SettingActivity.Companion.WEB_TERM_SERVICE
import kr.genti.presentation.databinding.BottomSheetTermBinding
import kr.genti.presentation.R as featureR

class TermBottomSheet :
    BaseBottomSheet<BottomSheetTermBinding>(featureR.layout.bottom_sheet_term) {
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
            btnTermPrivateDetail.setOnSingleClickListener {
                Intent(Intent.ACTION_VIEW, Uri.parse(WEB_TERM_PRIVATE)).apply {
                    startActivity(this)
                }
            }
            btnTermServiceDetail.setOnSingleClickListener {
                Intent(Intent.ACTION_VIEW, Uri.parse(WEB_TERM_SERVICE)).apply {
                    startActivity(this)
                }
            }
        }
    }

    private fun initSubmitBtnListener() {
        binding.btnSubmit.setOnSingleClickListener {
            viewModel.clickSubmitBtn(true)
            dismiss()
        }
    }
}
