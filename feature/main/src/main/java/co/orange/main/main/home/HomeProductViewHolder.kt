package co.orange.main.main.home

import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView
import co.orange.core.extension.breakLines
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.setOverThousand
import co.orange.core.extension.setPriceForm
import co.orange.domain.entity.response.ProductModel
import co.orange.main.databinding.ItemHomeProductBinding
import coil.load

class HomeProductViewHolder(
    val binding: ItemHomeProductBinding,
    val productClick: (String, Int) -> (Unit),
    val likeClick: (String, Boolean, Int) -> (Unit),
) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(
        item: ProductModel,
        position: Int,
    ) {
        with(binding) {
            root.setOnSingleClickListener { productClick(item.productId, position) }

            btnItemLike.setOnSingleClickListener {
                likeClick(item.productId, item.isInterested, position)
            }

            tvHomeItemTitle.text = item.name.breakLines()
            ivHomeItem.load(item.imgUrl)

            tvHomeItemRealPrice.apply {
                text = item.originPrice.setPriceForm()
                paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }
            tvHomeItemNowPrice.text = item.salePrice.setPriceForm()
            tvHomeItemLike.text = item.interestCount.setOverThousand()
            btnItemLike.isSelected = item.isInterested
        }
    }
}
