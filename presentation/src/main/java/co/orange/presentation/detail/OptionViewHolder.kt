package co.orange.presentation.detail

import androidx.recyclerview.widget.RecyclerView
import co.orange.domain.entity.response.OptionModel
import kr.genti.core.extension.setOnSingleClickListener
import kr.genti.presentation.databinding.ItemOptionBinding

class OptionViewHolder(
    val binding: ItemOptionBinding,
    private val itemClick: (Int, Long) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: OptionModel, position: Int) {
        // TODO: 수정된 UI 대응
        with(binding) {
            tvOptionItemTitle.text = item.type
            root.setOnSingleClickListener {
                itemClick(position, item.optionId)
            }
        }
    }
}