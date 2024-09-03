package co.orange.presentation.sell.confirm

import android.os.Bundle
import androidx.activity.viewModels
import co.orange.core.base.BaseActivity
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ActivitySellConfirmBinding

class SellConfirmActivity :
    BaseActivity<ActivitySellConfirmBinding>(R.layout.activity_sell_confirm) {
    private val viewModel by viewModels<SellConfirmViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
