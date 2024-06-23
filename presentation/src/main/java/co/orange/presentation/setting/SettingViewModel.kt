package co.orange.presentation.setting

import androidx.lifecycle.ViewModel
import co.orange.domain.entity.response.SettingModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel
    @Inject
    constructor(
        // private val feedRepository: FeedRepository,
    ) : ViewModel() {
        val mockSettingModel =
            SettingModel(
                "김상호",
                "행복한물개물개물개",
                "010-1234-5678",
            )
    }
