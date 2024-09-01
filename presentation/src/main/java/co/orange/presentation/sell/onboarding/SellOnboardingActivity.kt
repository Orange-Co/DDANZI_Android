package co.orange.presentation.sell.onboarding

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import co.orange.core.base.BaseActivity
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.setStatusBarColorFromResource
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

    private lateinit var photoPickerResult: ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var galleryPickerResult: ActivityResultLauncher<Intent>

    private var sellProductDialog: SellProductDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initSelectBtnListener()
        setGalleryImageWithPhotoPicker()
        setGalleryImageWithGalleryPicker()
        observeCheckedAgainState()
        observeChangeImageState()
    }

    private fun initSelectBtnListener() {
        binding.btnSelect.setOnSingleClickListener {
            checkAndGetImage()
        }
    }

    private fun checkAndGetImage() {
        if (ActivityResultContracts.PickVisualMedia.isPhotoPickerAvailable(this)) {
            photoPickerResult.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        } else {
            galleryPickerResult.launch(
                Intent(Intent.ACTION_PICK).apply { type = "image/*" },
            )
        }
    }

    private fun setGalleryImageWithPhotoPicker() {
        photoPickerResult =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                uri?.let { viewModel.startSendingImage(it, this.contentResolver) }
            }
    }

    private fun setGalleryImageWithGalleryPicker() {
        galleryPickerResult =
            registerForActivityResult(
                ActivityResultContracts.StartActivityForResult(),
            ) { result ->
                when (result.resultCode) {
                    Activity.RESULT_OK ->
                        result.data?.data?.let {
                            viewModel.startSendingImage(it, this.contentResolver)
                        }

                    Activity.RESULT_CANCELED -> return@registerForActivityResult
                    else -> toast(getString(R.string.sell_product_picker_error))
                }
            }
    }

    private fun observeCheckedAgainState() {
        viewModel.isCheckedAgain.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { isChecked ->
                if (isChecked) {
                    checkAndGetImage()
                    viewModel.setCheckedAgain(false)
                }
            }.launchIn(lifecycleScope)
    }

    private fun observeChangeImageState() {
        viewModel.changingImageState.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { state ->
                when (state) {
                    is UiState.Success -> {
                        setLoadingScreen(false)
                        sellProductDialog = SellProductDialog()
                        sellProductDialog?.show(supportFragmentManager, SELL_PRODUCT_DIALOG)
                        viewModel.resetProductIdState()
                    }

                    is UiState.Failure -> {
                        setLoadingScreen(false)
                        toast(stringOf(R.string.error_msg))
                    }

                    is UiState.Loading -> setLoadingScreen(true)
                    is UiState.Empty -> return@onEach
                }
            }.launchIn(lifecycleScope)
    }

    private fun setLoadingScreen(isLoading: Boolean) {
        if (isLoading) {
            setStatusBarColorFromResource(R.color.background_50)
            binding.layoutLoading.isVisible = true
        } else {
            setStatusBarColorFromResource(R.color.white)
            binding.layoutLoading.isVisible = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sellProductDialog = null
    }

    companion object {
        private const val SELL_PRODUCT_DIALOG = "SELL_PRODUCT_DIALOG"
    }
}
