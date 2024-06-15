package co.orange.presentation.detail

import androidx.recyclerview.widget.RecyclerView
import co.orange.domain.entity.response.OptionModel.OptionDetailModel
import kr.genti.core.extension.setOnSingleClickListener
import kr.genti.presentation.databinding.ItemOptionDetailBinding

class OptionDetailViewHolder(
    val binding: ItemOptionDetailBinding,
    private val itemClick: (Int, Long) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: OptionDetailModel, position: Int) {
        with(binding) {
            tvOptionItemDetailTitle.text = item.content
            root.setOnSingleClickListener {
                itemClick(position, item.optionDetailId)
            }
        }
    }
}