package co.orange.presentation.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import co.orange.core.util.ItemDiffCallback
import co.orange.domain.entity.response.ProductOptionModel
import kr.genti.presentation.databinding.ItemOptionBinding

class OptionAdapter(
    private val itemClick: (Long, Long) -> Unit,
) : ListAdapter<ProductOptionModel, OptionViewHolder>(diffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): OptionViewHolder {
        val inflater by lazy { LayoutInflater.from(parent.context) }
        val binding: ItemOptionBinding =
            ItemOptionBinding.inflate(inflater, parent, false)
        return OptionViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(
        holder: OptionViewHolder,
        position: Int,
    ) {
        val item = getItem(position) ?: return
        holder.onBind(item, position)
    }

    fun addList(newItems: List<ProductOptionModel>) {
        val currentItems = currentList.toMutableList()
        currentItems.addAll(newItems)
        submitList(currentItems)
    }

    companion object {
        private val diffUtil =
            ItemDiffCallback<ProductOptionModel>(
                onItemsTheSame = { old, new -> old.optionId == new.optionId },
                onContentsTheSame = { old, new -> old == new },
            )
    }
}
