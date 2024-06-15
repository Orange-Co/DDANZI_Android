package co.orange.presentation.main.home

import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView
import co.orange.domain.entity.response.ProductModel
import coil.load
import kr.genti.core.extension.breakLines
import kr.genti.core.extension.setNumberForm
import kr.genti.core.extension.setOnSingleClickListener
import kr.genti.core.extension.setOverThousand
import kr.genti.presentation.databinding.ItemHomeProductBinding

class HomeProductViewHolder(
    val binding: ItemHomeProductBinding,
    val productClick: (ProductModel) -> (Unit),
    val likeClick: (Unit) -> (Unit),
) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: ProductModel) {
        with(binding) {
            btnItemLike.setOnSingleClickListener { likeClick }
            root.setOnSingleClickListener { productClick(item) }

            tvHomeItemTitle.text = item.name.breakLines()
            ivHomeItem.load(item.imgUrl)

            tvHomeItemRealPrice.apply {
                text = item.originPrice.setNumberForm()
                setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG)
            }
            tvHomeItemNowPrice.text = item.salePrice.setNumberForm()
            tvHomeItemLike.text = item.interestCount.setOverThousand()

        }
    }
}