package co.orange.presentation.setting.bank

import androidx.lifecycle.ViewModel
import co.orange.domain.entity.response.AddressInfoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BankViewModel
    @Inject
    constructor(
        // private val feedRepository: FeedRepository,
    ) : ViewModel() {
        val mockAddressModel =
            AddressInfoModel(
                "김상호",
                "04567",
                "서울특벌시 성동구 성수이로 137 107동 903호",
                "010-3259-0327",
            )
    }
