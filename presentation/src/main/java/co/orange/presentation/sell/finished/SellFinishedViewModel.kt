package co.orange.presentation.sell.finished

import androidx.lifecycle.ViewModel
import co.orange.domain.entity.response.SellRegisteredModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SellFinishedViewModel
    @Inject
    constructor(
        // private val feedRepository: FeedRepository,
    ) : ViewModel() {
        var itemId: String = ""

        val mockSellInfo =
            SellRegisteredModel(
                "123e4567-e89b-12d3-a456-426614174000",
                "딴지 키링 세트",
                9000,
            )
    }
