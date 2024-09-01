package co.orange.presentation.sell.onboarding

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.activityViewModels
import co.orange.core.base.BaseDialog
import co.orange.core.extension.setOnSingleClickListener
import co.orange.presentation.sell.progress.SellProgressActivity
import kr.genti.presentation.R
import kr.genti.presentation.databinding.DialogSellProductBinding

class SellProductDialog :
    BaseDialog<DialogSellProductBinding>(R.layout.dialog_sell_product) {
    private val viewModel by activityViewModels<SellOnboardingViewModel>()

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
            )
            setBackgroundDrawableResource(R.color.transparent)
        }
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        initExitBtnListener()
        initConfirmBtnListener()
        initPickAgainBtnListener()
        setSellProductUi()
    }

    private fun initExitBtnListener() {
        binding.btnExit.setOnSingleClickListener { dismiss() }
    }

    private fun initConfirmBtnListener() {
        binding.btnSubmit.setOnSingleClickListener {
            // TODO intent값 수정
            SellProgressActivity.createIntent(requireContext(), -1).apply {
                startActivity(this)
            }
            dismiss()
        }
    }

    private fun initPickAgainBtnListener() {
        binding.btnPickAgain.setOnSingleClickListener {
            viewModel.setCheckedAgain(true)
            dismiss()
        }
    }

    private fun setSellProductUi() {
        with(binding) {
            // TODO
            // ivSellProduct.load()
            tvSellProductName.text = "퓨퓨어 오일 퍼퓸 긴제목 텍스트트트트트트"
        }
    }
}
