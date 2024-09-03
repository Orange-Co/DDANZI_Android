package co.orange.presentation.history.interest

import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView
import co.orange.core.extension.breakLines
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.setOverThousand
import co.orange.core.extension.setPriceForm
import co.orange.domain.entity.response.ProductModel
import coil.load
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ItemHomeProductBinding

class HistoryInterestViewHolder(
    val binding: ItemHomeProductBinding,
    val itemClick: (String) -> (Unit),
) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: ProductModel) {
        with(binding) {
            tvHomeItemTitle.text = item.name.breakLines()
            ivHomeItem.load(item.imgUrl)
            tvHomeItemRealPrice.apply {
                text = item.originPrice.setPriceForm()
                paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }
            tvHomeItemNowPrice.text = item.salePrice.setPriceForm()
            tvHomeItemLike.text = item.interestCount.setOverThousand()
            btnItemLike.load(R.drawable.ic_like_yellow)

            root.setOnSingleClickListener {
                itemClick(item.productId)
            }
        }
    }
}
