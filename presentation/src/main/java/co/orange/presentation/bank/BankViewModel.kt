package co.orange.presentation.bank

import androidx.lifecycle.ViewModel
import co.orange.domain.entity.response.BankModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BankViewModel
    @Inject
    constructor(
        // private val feedRepository: FeedRepository,
    ) : ViewModel() {
        var accountId: Long = -1
        val mockBankModel =
            BankModel(
                0,
                "김상호",
                "우리은행",
                "82-12341234-1234",
            )
    }
