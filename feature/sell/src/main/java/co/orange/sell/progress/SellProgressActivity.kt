package co.orange.sell.progress

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import co.orange.core.R
import co.orange.core.amplitude.AmplitudeManager
import co.orange.core.base.BaseActivity
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.setPriceForm
import co.orange.core.extension.setStatusBarColorFromResource
import co.orange.core.extension.stringOf
import co.orange.core.extension.toast
import co.orange.core.navigation.NavigationManager
import co.orange.core.state.UiState
import co.orange.domain.entity.response.SellProductModel
import co.orange.domain.entity.response.SellRegisteredModel
import co.orange.sell.databinding.ActivitySellProgressBinding
import co.orange.sell.finished.SellFinishedActivity
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import co.orange.sell.R as featureR

@AndroidEntryPoint
class SellProgressActivity :
    BaseActivity<ActivitySellProgressBinding>(featureR.layout.activity_sell_progress) {
    @Inject
    lateinit var navigationManager: NavigationManager

    private val viewModel by viewModels<SellProgressViewModel>()

    private var sellDateBottomSheet: SellDateBottomSheet? = null
    private var bankDialog: BankDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initExitBtnListener()
        initTermDetailBtnListener()
        initRegisterBtnListener()
        initDateBtnListener()
        observeGetProductState()
        observePostRegisterState()
        observeLoadingState()
    }

    override fun onResume() {
        super.onResume()
        getProductWithIdFromIntent()
    }

    private fun initView() {
        binding.vm = viewModel
        AmplitudeManager.trackEvent("view_sell", mapOf("product_id" to viewModel.productId))
    }

    private fun initExitBtnListener() {
        binding.btnExit.setOnSingleClickListener {
            AmplitudeManager.trackEvent("click_sell_quit")
            finish()
        }
    }

    private fun initTermDetailBtnListener() {
        with(binding) {
            btnTermServiceDetail.setOnSingleClickListener {
                Intent(Intent.ACTION_VIEW, Uri.parse(WEB_TERM_SERVICE)).apply {
                    startActivity(this)
                }
            }
            btnTermSellDetail.setOnSingleClickListener {
                Intent(Intent.ACTION_VIEW, Uri.parse(WEB_TERM_SELL)).apply {
                    startActivity(this)
                }
            }
        }
    }

    private fun initRegisterBtnListener() {
        binding.btnRegister.setOnSingleClickListener {
            AmplitudeManager.trackEvent(
                "click_sell_next",
                mapOf("product_id" to viewModel.productId),
            )
            with(viewModel) {
                setLoadingState(true)
                if (isBankExist) {
                    postToRegisterProduct()
                } else {
                    bankDialog = BankDialog()
                    bankDialog?.show(supportFragmentManager, DIALOG_BANK)
                }
            }
        }
    }

    private fun initDateBtnListener() {
        with(binding) {
            btnSellDate.setOnSingleClickListener { startSelectingDate() }
            tvSellDate.setOnSingleClickListener { startSelectingDate() }
        }
    }

    private fun startSelectingDate() {
        AmplitudeManager.trackEvent("click_sell_date")
        sellDateBottomSheet = SellDateBottomSheet()
        sellDateBottomSheet?.show(supportFragmentManager, BOTTOM_SHEET_DATE)
    }

    private fun getProductWithIdFromIntent() {
        with(viewModel) {
            intent.getStringExtra(EXTRA_PRODUCT_ID)?.let { productId = it }
            intent.getStringExtra(EXTRA_PRODUCT_NAME)?.let { productName = it }
            intent.getStringExtra(EXTRA_IMAGE_URL)?.let { uploadedUrl = it }
            getProductWIthId()
        }
    }

    private fun observeGetProductState() {
        viewModel.getProductState.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { state ->
                when (state) {
                    is UiState.Success -> {
                        setIntentUi(state.data)
                        with(viewModel) {
                            if (isSentToBank) {
                                if (isBankExist) postToRegisterProduct()
                                isSentToBank = false
                                setLoadingState(false)
                            }
                        }
                    }

                    is UiState.Failure -> {
                        toast(stringOf(R.string.error_msg))
                        finish()
                    }

                    else -> return@onEach
                }
            }.launchIn(lifecycleScope)
    }

    private fun setIntentUi(item: SellProductModel) {
        with(binding) {
            tvSellInfoName.text = item.productName
            tvSellInfoOriginPrice.text = item.originPrice.setPriceForm()
            tvSellInfoSellPrice.text = item.salePrice.setPriceForm()
            ivSellProduct.load(item.imgUrl)
        }
    }

    private fun observePostRegisterState() {
        viewModel.postRegisterState.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { state ->
                when (state) {
                    is UiState.Success -> navigateToPushOrFinish(state.data)

                    is UiState.Failure -> {
                        toast(stringOf(R.string.error_msg))
                        viewModel.setLoadingState(false)
                    }

                    else -> return@onEach
                }
            }.launchIn(lifecycleScope)
    }

    private fun navigateToPushOrFinish(item: SellRegisteredModel) {
        if (isPermissionNeeded()) {
            navigateToPushActivity(item)
        } else {
            AmplitudeManager.updateProperty("user_push", "enabled")
            navigateToSellFinishedActivity(item)
        }
    }

    private fun isPermissionNeeded(): Boolean =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        } else {
            false
        }

    private fun navigateToSellFinishedActivity(item: SellRegisteredModel) {
        SellFinishedActivity.createIntent(
            this,
            item.itemId,
            item.productName,
            item.imgUrl,
            item.salePrice,
        ).apply { startActivity(this) }
        finish()
    }

    private fun navigateToPushActivity(item: SellRegisteredModel) {
        navigationManager.toAlarmRequestViewWithIntent(
            this,
            false,
            null,
            item.itemId,
            item.productName,
            item.imgUrl,
            item.salePrice,
        )
        finish()
    }

    private fun observeLoadingState() {
        viewModel.loadingState.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { isLoading ->
                if (isLoading) {
                    setStatusBarColorFromResource(R.color.background_50)
                    binding.layoutLoading.isVisible = true
                } else {
                    setStatusBarColorFromResource(R.color.white)
                    binding.layoutLoading.isVisible = false
                }
            }.launchIn(lifecycleScope)
    }

    override fun onDestroy() {
        super.onDestroy()
        sellDateBottomSheet = null
        bankDialog = null
    }

    companion object {
        private const val BOTTOM_SHEET_DATE = "BOTTOM_SHEET_DATE"
        private const val DIALOG_BANK = " DIALOG_BANK"

        const val WEB_TERM_SERVICE =
            "https://brawny-guan-098.notion.site/faa1517ffed44f6a88021a41407ed736?pvs=4"
        const val WEB_TERM_SELL =
            "https://brawny-guan-098.notion.site/6d77260d027148ceb0f806f0911c284a?pvs=4"

        private const val EXTRA_PRODUCT_ID = "EXTRA_PRODUCT_ID"
        private const val EXTRA_PRODUCT_NAME = "EXTRA_PRODUCT_NAME"
        private const val EXTRA_IMAGE_URL = "EXTRA_IMAGE_URL"

        @JvmStatic
        fun createIntent(
            context: Context,
            productId: String,
            productName: String,
            imageUrl: String,
        ): Intent =
            Intent(context, SellProgressActivity::class.java).apply {
                putExtra(EXTRA_PRODUCT_ID, productId)
                putExtra(EXTRA_PRODUCT_NAME, productName)
                putExtra(EXTRA_IMAGE_URL, imageUrl)
            }
    }
}
