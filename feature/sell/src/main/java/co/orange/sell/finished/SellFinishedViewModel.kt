package co.orange.sell.finished

import androidx.lifecycle.ViewModel
import co.orange.domain.repository.SellRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SellFinishedViewModel
    @Inject
    constructor(
        private val sellRepository: SellRepository,
    ) : ViewModel() {
        var itemId: String = ""
    }
