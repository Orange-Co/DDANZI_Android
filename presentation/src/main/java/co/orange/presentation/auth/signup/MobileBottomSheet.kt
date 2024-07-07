package co.orange.presentation.auth.signup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import co.orange.domain.enums.MobileType
import kr.genti.core.base.BaseBottomSheet
import kr.genti.core.extension.setOnSingleClickListener
import kr.genti.presentation.R
import kr.genti.presentation.databinding.BottomSheetMobileBinding

class MobileBottomSheet :
    BaseBottomSheet<BottomSheetMobileBinding>(R.layout.bottom_sheet_mobile) {
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

        initBtnsListener()
    }

    private fun initBtnsListener() {
        with(binding) {
            btnMobileSkt.setOnSingleClickListener { setMobile(MobileType.SKT) }
            btnMobileKt.setOnSingleClickListener { setMobile(MobileType.KT) }
            btnMobileLg.setOnSingleClickListener { setMobile(MobileType.LG) }
            btnMobileSktA.setOnSingleClickListener { setMobile(MobileType.SKT_A) }
            btnMobileKtA.setOnSingleClickListener { setMobile(MobileType.KT_A) }
            btnMobileLgA.setOnSingleClickListener { setMobile(MobileType.LG_A) }
        }
    }

    private fun setMobile(mobileType: MobileType) {
        with(viewModel) {
            mobile.value = mobileType.description
            isMobileFinished.value = true
        }
        dismiss()
    }
}
