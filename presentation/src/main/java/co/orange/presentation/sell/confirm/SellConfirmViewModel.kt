package co.orange.presentation.sell.confirm

import androidx.lifecycle.ViewModel
import co.orange.domain.entity.response.SellProductModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SellConfirmViewModel
    @Inject
    constructor(
        // private val feedRepository: FeedRepository,
    ) : ViewModel() {
        var productId: Long = -1

        var mockSellInfo =
            SellProductModel(
                1102303002,
                "딴지 키링 세트",
                9000,
                7000,
            )
    }
