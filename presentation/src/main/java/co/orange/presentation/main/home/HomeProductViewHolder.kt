package co.orange.presentation.main.home

import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView
import co.orange.core.extension.breakLines
import co.orange.core.extension.setNumberForm
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.setOverThousand
import co.orange.domain.entity.response.ProductModel
import coil.load
import kr.genti.presentation.databinding.ItemHomeProductBinding

class HomeProductViewHolder(
    val binding: ItemHomeProductBinding,
    val productClick: (ProductModel) -> (Unit),
    val likeClick: (String, Boolean, Int) -> (Unit),
) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(
        item: ProductModel,
        position: Int,
    ) {
        with(binding) {
            root.setOnSingleClickListener { productClick(item) }

            btnItemLike.setOnSingleClickListener {
                likeClick(item.productId, item.isInterested, position)
            }

            tvHomeItemTitle.text = item.name.breakLines()
            ivHomeItem.load(item.imgUrl)

            tvHomeItemRealPrice.apply {
                text = item.originPrice.setNumberForm()
                paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }
            tvHomeItemNowPrice.text = item.salePrice.setNumberForm()
            tvHomeItemLike.text = item.interestCount.setOverThousand()
            btnItemLike.isSelected = item.isInterested
        }
    }

    fun updateItemLikeState() {
        binding.btnItemLike.isSelected = !binding.btnItemLike.isSelected
    }
}
