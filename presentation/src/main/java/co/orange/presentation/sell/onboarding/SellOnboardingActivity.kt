package co.orange.presentation.sell.onboarding

import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import co.orange.core.base.BaseActivity
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.stringOf
import co.orange.core.extension.toast
import co.orange.core.state.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivitySellOnboardingBinding

@AndroidEntryPoint
class SellOnboardingActivity :
    BaseActivity<ActivitySellOnboardingBinding>(R.layout.activity_sell_onboarding) {
    private val viewModel by viewModels<SellOnboardingViewModel>()

    private lateinit var activityResult: ActivityResultLauncher<PickVisualMediaRequest>
    private var sellProductDialog: SellProductDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initSelectBtnListener()
        findSelectedImage()
        observeCheckedAgainState()
        observeGetProductIdState()
    }

    private fun initSelectBtnListener() {
        binding.btnSelect.setOnSingleClickListener {
            activityResult.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    private fun findSelectedImage() {
        activityResult =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) viewModel.showCaptureImage(uri, this)
            }
    }

    private fun observeCheckedAgainState() {
        viewModel.isCheckedAgain.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { isChecked ->
                if (isChecked) {
                    activityResult.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    viewModel.setCheckedAgain(false)
                }
            }.launchIn(lifecycleScope)
    }

    private fun observeGetProductIdState() {
        viewModel.getProductIdState.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { state ->
                when (state) {
                    is UiState.Success -> {
                        sellProductDialog = SellProductDialog()
                        sellProductDialog?.show(supportFragmentManager, SELL_PRODUCT_DIALOG)
                        viewModel.resetProductIdState()
                    }

                    is UiState.Failure -> toast(stringOf(R.string.error_msg))
                    else -> return@onEach
                }
            }.launchIn(lifecycleScope)
    }

    override fun onDestroy() {
        super.onDestroy()
        sellProductDialog = null
    }

    companion object {
        private const val SELL_PRODUCT_DIALOG = "SELL_PRODUCT_DIALOG"
    }
}
