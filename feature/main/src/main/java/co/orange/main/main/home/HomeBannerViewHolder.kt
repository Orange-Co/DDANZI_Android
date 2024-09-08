package co.orange.main.main.home

import androidx.recyclerview.widget.RecyclerView
import co.orange.core.extension.setOnSingleClickListener
import co.orange.main.databinding.ItemHomeBannerBinding
import coil.load

class HomeBannerViewHolder(
    val binding: ItemHomeBannerBinding,
    val bannerClick: (String) -> (Unit),
) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: String) {
        with(binding) {
            ivHomeBanner.load(item)
            root.setOnSingleClickListener {
                bannerClick(item)
            }
        }
    }
}
