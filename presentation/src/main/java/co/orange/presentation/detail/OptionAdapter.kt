package co.orange.presentation.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import co.orange.domain.entity.response.OptionModel
import kr.genti.core.util.ItemDiffCallback
import kr.genti.presentation.databinding.ItemOptionBinding

class OptionAdapter(
    private val itemClick: (Int, Long) -> Unit,
) : ListAdapter<OptionModel, OptionViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val inflater by lazy { LayoutInflater.from(parent.context) }
        val binding: ItemOptionBinding =
            ItemOptionBinding.inflate(inflater, parent, false)
        return OptionViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.onBind(item, position)
    }

    fun addList(newItems: List<OptionModel>) {
        val currentItems = currentList.toMutableList()
        currentItems.addAll(newItems)
        submitList(currentItems)
    }

    companion object {
        private val diffUtil = ItemDiffCallback<OptionModel>(
            onItemsTheSame = { old, new -> old.optionId == new.optionId },
            onContentsTheSame = { old, new -> old == new },
        )
    }
}