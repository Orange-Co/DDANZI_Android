package co.orange.presentation.main.home

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import co.orange.core.base.BaseFragment
import co.orange.core.extension.dpToPx
import co.orange.core.extension.initOnBackPressedListener
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.stringOf
import co.orange.core.extension.toast
import co.orange.core.state.UiState
import co.orange.domain.entity.response.ProductModel
import co.orange.presentation.auth.login.LoginActivity
import co.orange.presentation.detail.DetailActivity
import co.orange.presentation.main.home.HomeAdapter.Companion.VIEW_TYPE_BANNER
import co.orange.presentation.search.SearchActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kr.genti.presentation.R
import kr.genti.presentation.databinding.FragmentHomeBinding

@AndroidEntryPoint
class HomeFragment() : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    lateinit var activityResult: ActivityResultLauncher<PickVisualMediaRequest>

    private var _adapter: HomeAdapter? = null
    val adapter
        get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    private val viewModel by activityViewModels<HomeViewModel>()

    private var sellProductDialog: SellProductDialog? = null

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        moveToLogin()
        initView()
        initAdapter()
        initSearchBtnListener()
        initSellBtnListener()
        searchSellProduct()
        setDeviceToken()
        setGridRecyclerView()
        setRecyclerViewDeco()
        observeCheckedAgainState()
        observeGetHomeDataState()
        observeGetProductIdState()
    }

    private fun initView() {
        initOnBackPressedListener(binding.root)
    }

    // TODO 삭제
    private fun moveToLogin() {
        binding.ivHomeLogo.setOnSingleClickListener {
            Intent(requireActivity(), LoginActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun initAdapter() {
        _adapter =
            HomeAdapter(
                bannerClick = ::initBannerClickListener,
                productClick = ::initProductClickListener,
                likeClick = ::initLikeClickListener,
            )
        binding.rvHome.adapter = adapter
    }

    // TODO: 버튼 리스너 설정
    private fun initBannerClickListener(unit: Unit) {}

    private fun initProductClickListener(item: ProductModel) {
        DetailActivity.createIntent(
            requireContext(),
            item.productId,
            item.imgUrl,
            item.originPrice,
            item.salePrice,
        )
            .apply { startActivity(this) }
    }

    private fun initLikeClickListener(unit: Unit) {}

    private fun initSearchBtnListener() {
        binding.btnSearch.setOnSingleClickListener {
            Intent(requireContext(), SearchActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun initSellBtnListener() {
        binding.btnSell.setOnSingleClickListener {
            activityResult.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
        }
    }

    private fun searchSellProduct() {
        activityResult =
            registerForActivityResult(PickVisualMedia()) { uri ->
                if (uri != null) {
                    viewModel.selectedImageUri = uri
                    viewModel.showCaptureImage(uri, requireContext())
                }
            }
    }

    private fun setDeviceToken() {
        // TODO : FCM의 디바이스토큰으로 수정
        viewModel.setDeviceToken(
            Settings.Secure.getString(
                requireContext().contentResolver,
                Settings.Secure.ANDROID_ID,
            ),
        )
    }

    private fun setGridRecyclerView() {
        binding.rvHome.layoutManager =
            GridLayoutManager(context, 2).apply {
                spanSizeLookup =
                    object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            return when (adapter.getItemViewType(position)) {
                                VIEW_TYPE_BANNER -> 2
                                else -> 1
                            }
                        }
                    }
            }
    }

    private fun setRecyclerViewDeco() {
        binding.rvHome.addItemDecoration(
            GridItemDecoration(
                spanCount = 2,
                spacing = 30.dpToPx(requireContext()),
                bottomPadding = 50.dpToPx(requireContext()),
            ),
        )
    }

    private fun observeCheckedAgainState() {
        viewModel.isCheckedAgain.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { isChecked ->
                if (isChecked) {
                    activityResult.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
                    viewModel.setCheckedState(false)
                }
            }.launchIn(lifecycleScope)
    }

    private fun observeGetHomeDataState() {
        viewModel.getHomeDataState.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { state ->
                when (state) {
                    is UiState.Success -> {
                        adapter.addBannerItem(state.data.homeImgUrl)
                        adapter.setItemList(state.data.productList)
                    }

                    is UiState.Failure -> toast(stringOf(R.string.error_msg))
                    else -> return@onEach
                }
            }.launchIn(lifecycleScope)
    }

    private fun observeGetProductIdState() {
        viewModel.getProductIdState.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { state ->
                when (state) {
                    is UiState.Success -> {
                        sellProductDialog = SellProductDialog()
                        sellProductDialog?.show(parentFragmentManager, SELL_PRODUCT_DIALOG)
                    }

                    is UiState.Failure -> toast(stringOf(R.string.error_msg))
                    else -> return@onEach
                }
            }.launchIn(lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _adapter = null
        sellProductDialog = null
    }

    companion object {
        private const val SELL_PRODUCT_DIALOG = "SELL_PRODUCT_DIALOG"
    }
}
