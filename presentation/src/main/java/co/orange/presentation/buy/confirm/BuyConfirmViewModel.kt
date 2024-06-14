package co.orange.presentation.buy.confirm

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BuyConfirmViewModel
@Inject
constructor(
    // private val feedRepository: FeedRepository,
) : ViewModel() {

    var imageUrl: String = ""
    var originPrice: Int = 0
    var detailPrice: Int = 0
    var name: String = ""

}