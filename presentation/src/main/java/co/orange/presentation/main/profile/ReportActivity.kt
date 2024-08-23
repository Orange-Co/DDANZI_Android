package co.orange.presentation.main.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import co.orange.core.base.BaseActivity
import co.orange.core.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivityReportBinding

@AndroidEntryPoint
class ReportActivity : BaseActivity<ActivityReportBinding>(R.layout.activity_report) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBackBtnListener()
        initChannelTalkBtnListener()
    }

    private fun initBackBtnListener() {
        binding.btnBack.setOnSingleClickListener { finish() }
    }

    private fun initChannelTalkBtnListener() {
        binding.btnEnterChannel.setOnSingleClickListener {
            Intent(Intent.ACTION_VIEW, Uri.parse(WEB_CHANNEL_TALK)).apply {
                startActivity(this)
            }
        }
    }

    companion object {
        const val WEB_CHANNEL_TALK = "https://open.kakao.com/o/sJ9nJsKg"
    }
}
