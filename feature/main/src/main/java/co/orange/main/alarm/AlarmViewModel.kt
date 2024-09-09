package co.orange.main.alarm

import androidx.lifecycle.ViewModel
import co.orange.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel
    @Inject
    constructor(
        private val homeRepository: HomeRepository,
    ) : ViewModel()
