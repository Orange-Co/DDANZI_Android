package co.orange.presentation.sell.finished

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class SellFinishedViewModel : ViewModel() {
    var itemId: String = ""
}
