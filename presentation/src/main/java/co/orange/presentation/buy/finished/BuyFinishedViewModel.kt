package co.orange.presentation.buy.finished

import androidx.lifecycle.ViewModel
import co.orange.domain.entity.response.AddressInfoModel
import co.orange.domain.entity.response.BuyInfoModel
import co.orange.domain.entity.response.PaymentInfoModel
import co.orange.domain.enums.OrderStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BuyFinishedViewModel
    @Inject
    constructor(
        // private val feedRepository: FeedRepository,
    ) : ViewModel() {
        var productId: String = ""

        val mockBuyInfo =
            BuyInfoModel(
                "123e4567-e89b-12d3-a456-426614174000",
                OrderStatus.ORDER_PLACED,
                "상품이름은 한줄로만 보여줄거에야야야야야야",
                "https://github.com/Marchbreeze/Marchbreeze/assets/97405341/cd2c0454-92b4-41e7-ae2f-319f83e2426f",
                24000,
                "등둔",
                listOf(
                    AddressInfoModel(
                        "김상호",
                        "04567",
                        "서울특벌시 성동구 성수이로 137 107동 903호",
                        "010-3259-0327",
                    ),
                ),
                listOf(
                    PaymentInfoModel(
                        "NAVERPAY",
                        "2024.06.06",
                    ),
                ),
                3000,
                350,
                21350,
            )
    }
