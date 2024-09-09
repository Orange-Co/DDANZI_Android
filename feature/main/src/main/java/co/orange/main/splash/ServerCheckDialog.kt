package co.orange.main.splash

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import co.orange.core.R
import co.orange.core.base.BaseDialog
import co.orange.core.extension.setOnSingleClickListener
import co.orange.main.databinding.DialogServerCheckBinding
import co.orange.main.R as featureR

class ServerCheckDialog :
    BaseDialog<DialogServerCheckBinding>(featureR.layout.dialog_server_check) {
    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
            )
            setBackgroundDrawableResource(R.color.transparent)
        }
        dialog?.apply {
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        initQuitBtnListener()
    }

    private fun initQuitBtnListener() {
        binding.btnQuit.setOnSingleClickListener {
            dismiss()
            requireActivity().finish()
        }
    }
}
