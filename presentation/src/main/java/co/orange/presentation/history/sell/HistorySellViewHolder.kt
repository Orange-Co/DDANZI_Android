package co.orange.presentation.history.sell

import android.graphics.Paint
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import co.orange.core.extension.breakLines
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.setOverThousand
import co.orange.core.extension.setPriceForm
import co.orange.domain.entity.response.HistorySellModel
import coil.load
import kr.genti.presentation.databinding.ItemHomeProductBinding

class HistorySellViewHolder(
    val binding: ItemHomeProductBinding,
    val itemClick: (String) -> (Unit),
) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: HistorySellModel.ItemProductModel) {
        with(binding) {
            tvHomeItemTitle.text = item.productName.breakLines()
            ivHomeItem.load(item.imgUrl)
            tvHomeItemRealPrice.apply {
                text = item.originPrice.setPriceForm()
                paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }
            tvHomeItemNowPrice.text = item.salePrice.setPriceForm()
            tvHomeItemLike.text = item.interestCount.setOverThousand()
            btnItemLike.isVisible = false

            root.setOnSingleClickListener {
                itemClick(item.itemId)
            }
        }
    }
}
