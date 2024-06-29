package co.orange.presentation.sell.finished

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import co.orange.domain.entity.response.SellInfoModel
import co.orange.presentation.main.MainActivity
import co.orange.presentation.sell.info.SellInfoActivity
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import kr.genti.core.base.BaseActivity
import kr.genti.core.extension.setNumberForm
import kr.genti.core.extension.setOnSingleClickListener
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivitySellFinishedBinding

@AndroidEntryPoint
class SellFinishedActivity :
    BaseActivity<ActivitySellFinishedBinding>(R.layout.activity_sell_finished) {
    private val viewModel by viewModels<SellFinishedViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initReturnBtnListener()
        initDetailBtnListener()
        initSellMoreListener()
        getIntentInfo()
        setIntentUi(viewModel.mockSellInfo)
    }

    private fun initReturnBtnListener() {
        binding.btnExit.setOnSingleClickListener {
            Intent(this, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                startActivity(this)
            }
        }
    }

    private fun initDetailBtnListener() {
        binding.btnProductDetail.setOnSingleClickListener {
            SellInfoActivity.createIntent(this, viewModel.itemId).apply {
                startActivity(this)
            }
        }
    }

    private fun initSellMoreListener() {
        binding.btnSellMore.setOnSingleClickListener {
            // TODO
        }
    }

    private fun getIntentInfo() {
        viewModel.itemId = intent.getLongExtra(EXTRA_ITEM_ID, -1)
    }

    private fun setIntentUi(item: SellInfoModel) {
        with(binding) {
            // TODO 이미지 설정
            ivFinishedItem.load(R.drawable.mock_img_product)
            tvFinishedItemName.text = item.productName
            tvFinishedItemPrice.text = item.originPrice.setNumberForm()
        }
    }

    companion object {
        private const val EXTRA_ITEM_ID = "EXTRA_ITEM_ID"

        @JvmStatic
        fun createIntent(
            context: Context,
            itemId: Long,
        ): Intent =
            Intent(context, SellFinishedActivity::class.java).apply {
                putExtra(EXTRA_ITEM_ID, itemId)
            }
    }
}
