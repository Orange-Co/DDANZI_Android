package co.orange.presentation.sell.confirm

import androidx.lifecycle.ViewModel
import co.orange.domain.repository.SellRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SellConfirmViewModel
    @Inject
    constructor(
        private val sellRepository: SellRepository,
    ) : ViewModel()
