package co.orange.presentation.main.profile.history

import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView
import co.orange.core.extension.breakLines
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.extension.setPriceForm
import co.orange.domain.entity.response.HistoryBuyModel.OrderProductModel
import coil.load
import kr.genti.presentation.databinding.ItemHistoryBuyBinding

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

            root.setOnSingleClickListener {
                itemClick(item.orderId)
            }
        }
    }
}
