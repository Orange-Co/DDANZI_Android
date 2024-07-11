package co.orange.presentation.main.home

import androidx.recyclerview.widget.RecyclerView
import co.orange.core.extension.setOnSingleClickListener
import coil.load
import kr.genti.presentation.databinding.ItemHomeBannerBinding

class HomeBannerViewHolder(
    val binding: ItemHomeBannerBinding,
    val bannerClick: (Unit) -> (Unit),
) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: String) {
        with(binding) {
            ivHomeBanner.load(item)
            root.setOnSingleClickListener {
                bannerClick
            }
        }
    }
}
