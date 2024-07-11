package co.orange.presentation.main.home

import androidx.recyclerview.widget.RecyclerView
import co.orange.core.extension.setOnSingleClickListener
import co.orange.domain.entity.response.ProductModel
import coil.load
import kr.genti.presentation.databinding.ItemHomeBannerBinding

class HomeBannerViewHolder(
    val binding: ItemHomeBannerBinding,
    val bannerClick: (Unit) -> (Unit),
) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: ProductModel) {
        with(binding) {
            ivHomeBanner.load(item.imgUrl)
            root.setOnSingleClickListener {
                bannerClick
            }
        }
    }
}
