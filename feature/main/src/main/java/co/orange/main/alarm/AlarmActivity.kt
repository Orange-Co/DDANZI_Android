package co.orange.main.alarm

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import co.orange.core.base.BaseActivity
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.stringOf
import co.orange.core.extension.toast
import co.orange.core.navigation.NavigationManager
import co.orange.core.state.UiState
import co.orange.domain.entity.response.AlarmListModel.AlarmItemModel
import co.orange.domain.enums.AlarmType
import co.orange.main.R
import co.orange.main.databinding.ActivityAlarmBinding
import co.orange.main.main.profile.ReportActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class AlarmActivity : BaseActivity<ActivityAlarmBinding>(R.layout.activity_alarm) {
    @Inject
    lateinit var navigationManager: NavigationManager

    private val viewModel by viewModels<AlarmViewModel>()

    private var _adapter: AlarmListAdapter? = null
    val adapter
        get() = requireNotNull(_adapter) { getString(co.orange.core.R.string.adapter_not_initialized_error_msg) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBackBtnListener()
        initAdapter()
        observeGetAlarmListState()
        observePatchReadState()
    }

    private fun initBackBtnListener() {
        binding.btnBack.setOnSingleClickListener { finish() }
    }

    private fun initAdapter() {
        _adapter =
            AlarmListAdapter(
                itemClick = ::initItemClickListener,
            )
        binding.rvAlarm.adapter = adapter
    }

    private fun initItemClickListener(item: AlarmItemModel) {
        viewModel.patchToReadAlarmToServer(item)
    }

    private fun observeGetAlarmListState() {
        viewModel.getAlarmListState.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { state ->
                when (state) {
                    is UiState.Success -> {
                        adapter.submitList(state.data.alarmList)
                        binding.layoutEmpty.isVisible = state.data.alarmList.isEmpty()
                    }

                    is UiState.Failure -> {
                        toast(stringOf(co.orange.core.R.string.error_msg))
                        binding.layoutEmpty.isVisible = true
                    }

                    else -> return@onEach
                }
            }.launchIn(lifecycleScope)
    }

    private fun observePatchReadState() {
        viewModel.patchReadState.flowWithLifecycle(lifecycle).distinctUntilChanged()
            .onEach { state ->
                when (state) {
                    is UiState.Success -> navigateToViewByType(state.data)
                    is UiState.Failure -> toast(stringOf(co.orange.core.R.string.error_msg))
                    else -> return@onEach
                }
            }.launchIn(lifecycleScope)
    }

    private fun navigateToViewByType(item: AlarmItemModel) {
        when (item.alarmCase) {
            AlarmType.A1.name -> {
                item.orderId?.let { navigationManager.toSellConfirmView(this, it) }
            }

            AlarmType.A4.name -> {
                startActivity(Intent(this, ReportActivity::class.java))
            }

            in listOf(AlarmType.B2.name, AlarmType.B3.name, AlarmType.B4.name) -> {
                item.orderId?.let { navigationManager.toSellInfoView(this, it) }
            }

            else -> finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _adapter = null
    }
}
