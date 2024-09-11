package co.orange.setting.history.order

import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView
import co.orange.core.extension.breakLines
import co.orange.core.extension.convertDateTime
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.setPriceForm
import co.orange.domain.entity.response.HistoryBuyModel.OrderProductModel
import co.orange.setting.databinding.ItemHistoryBuyBinding
import coil.load

class HistoryBuyViewHolder(
    val binding: ItemHistoryBuyBinding,
    val itemClick: (String) -> (Unit),
) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: OrderProductModel) {
        with(binding) {
            tvBuyItemTitle.text = item.productName.breakLines()
            ivBuyItem.load(item.imgUrl)
            tvBuyItemRealPrice.apply {
                text = item.originPrice.setPriceForm()
                paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }
            tvBuyItemNowPrice.text = item.salePrice.setPriceForm()
            tvBuyItemDate.text =
                item.paidAt.convertDateTime(
                    OLD_DATE_PATTERN,
                    NEW_DATE_PATTERN,
                )

            root.setOnSingleClickListener {
                itemClick(item.orderId)
            }
        }
    }

    companion object {
        const val OLD_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss"
        const val NEW_DATE_PATTERN = "yyyy년 MM월 dd일 구매"
    }
}
