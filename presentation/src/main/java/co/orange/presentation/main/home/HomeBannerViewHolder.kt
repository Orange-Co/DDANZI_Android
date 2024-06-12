package co.orange.presentation.main.home

import androidx.recyclerview.widget.RecyclerView
import kr.genti.core.extension.setOnSingleClickListener
import kr.genti.presentation.databinding.ItemHomeBannerBinding

class HomeBannerViewHolder(
    val binding: ItemHomeBannerBinding,
    val bannerClick: (Unit) -> (Unit),
) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind() {
        binding.root.setOnSingleClickListener {
            bannerClick
        }
    }
}
