package co.orange.presentation.main.home

import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView
import co.orange.domain.entity.response.ProductModel
import coil.load
import kr.genti.core.extension.setOnSingleClickListener
import kr.genti.presentation.databinding.ItemHomeProductBinding
import java.text.DecimalFormat

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

            tvHomeItemTitle.text = item.name
            ivHomeItem.load(item.imgUrl)

            tvHomeItemRealPrice.apply {
                text = decimalString(item.originPrice)
                setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG)
            }
            tvHomeItemNowPrice.text = decimalString(item.salePrice)

            if (item.interestCount < 1000) {
                tvHomeItemLike.text = item.interestCount.toString()
            } else {
                tvHomeItemLike.text = OVER_999
            }

        }
    }

    fun decimalString(number: Int): String {
        val decimal = DecimalFormat("#,###")
        return decimal.format(number)
    }

    companion object {
        const val OVER_999 = "999+"
    }
}