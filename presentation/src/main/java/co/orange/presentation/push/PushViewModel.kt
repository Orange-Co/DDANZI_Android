package co.orange.presentation.push

import androidx.lifecycle.ViewModel
import co.orange.domain.repository.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PushViewModel
    @Inject
    constructor(
        private val settingRepository: SettingRepository,
    ) : ViewModel()
