package co.orange.auth.phone

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import co.orange.auth.databinding.BottomSheetTermBinding
import co.orange.core.R
import co.orange.core.amplitude.AmplitudeManager
import co.orange.core.base.BaseBottomSheet
import co.orange.core.extension.setOnSingleClickListener
import co.orange.auth.R as featureR

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
            AmplitudeManager.trackEvent("click_verification_terms_next")
            viewModel.clickSubmitBtn(true)
            dismiss()
        }
    }

    companion object {
        const val WEB_TERM_PRIVATE =
            "https://brawny-guan-098.notion.site/5a8b57e78f594988aaab08b8160c3072?pvs=4"
        const val WEB_TERM_SERVICE =
            "https://brawny-guan-098.notion.site/faa1517ffed44f6a88021a41407ed736?pvs=4"
    }
}
