package co.orange.setting.history.item

import android.graphics.Paint
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import co.orange.core.extension.breakLines
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.setOverThousand
import co.orange.core.extension.setPriceForm
import co.orange.domain.entity.response.HistorySellModel
import co.orange.setting.databinding.ItemHistoryItemBinding
import coil.load

class HistorySellViewHolder(
    val binding: ItemHistoryItemBinding,
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
