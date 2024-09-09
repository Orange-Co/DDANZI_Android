package co.orange.main.alarm

import android.os.Bundle
import androidx.activity.viewModels
import co.orange.core.base.BaseActivity
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.navigation.NavigationManager
import co.orange.domain.entity.response.AlarmListModel.AlarmItemModel
import co.orange.main.R
import co.orange.main.databinding.ActivityAlarmBinding
import dagger.hilt.android.AndroidEntryPoint
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
        // TODO
    }

    override fun onDestroy() {
        super.onDestroy()

        _adapter = null
    }
}
