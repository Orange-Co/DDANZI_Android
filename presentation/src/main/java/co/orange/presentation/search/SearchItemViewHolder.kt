package co.orange.presentation.search

import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView
import co.orange.core.extension.breakLines
import co.orange.core.extension.setNumberForm
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.setOverThousand
import co.orange.domain.entity.response.ProductModel
import coil.load
import kr.genti.presentation.databinding.ItemSearchProductBinding

class SearchItemViewHolder(
    val binding: ItemSearchProductBinding,
    val itemClick: (ProductModel) -> (Unit),
) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: ProductModel) {
        with(binding) {
            tvSearchItemTitle.text = item.name.breakLines()
            ivSearchItem.load(item.imgUrl)
            tvSearchItemRealPrice.apply {
                text = item.originPrice.setNumberForm()
                setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG)
            }
            tvSearchItemNowPrice.text = item.salePrice.setNumberForm()
            tvSearchItemLike.text = item.interestCount.setOverThousand()
            ivSearchItemLike.isSelected = item.isInterested

            root.setOnSingleClickListener {
                itemClick(item)
            }
        }
    }
}
