package co.orange.main.detail

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import co.orange.core.R
import co.orange.domain.entity.response.ProductOptionModel.OptionDetailModel
import co.orange.main.databinding.ItemOptionDetailBinding

class OptionDetailViewHolder(
    val binding: ItemOptionDetailBinding,
) : RecyclerView.ViewHolder(binding.root) {
    var isSelected = false

    fun onBind(item: OptionDetailModel) {
        with(binding) {
            tvOptionItemDetailTitle.text = item.content
            tvOptionItemDetailTitle.setTextColor(
                if (isSelected) {
                    ContextCompat.getColor(itemView.context, R.color.black)
                } else {
                    ContextCompat.getColor(itemView.context, R.color.gray_2)
                },
            )
        }
    }
}
