package co.orange.main.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import co.orange.core.extension.setOnSingleClickListener
import co.orange.core.util.ItemDiffCallback
import co.orange.domain.entity.response.ProductOptionModel.OptionDetailModel
import co.orange.main.databinding.ItemOptionDetailBinding

class OptionDetailAdapter(
    private val itemClick: (Long) -> Unit,
) : ListAdapter<OptionDetailModel, OptionDetailViewHolder>(diffUtil) {
    private var selectedPosition = -1

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): OptionDetailViewHolder {
        val inflater by lazy { LayoutInflater.from(parent.context) }
        val binding: ItemOptionDetailBinding =
            ItemOptionDetailBinding.inflate(inflater, parent, false)
        return OptionDetailViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: OptionDetailViewHolder,
        position: Int,
    ) {
        val item = getItem(position) ?: return
        holder.binding.root.setOnSingleClickListener {
            selectedPosition = holder.getAdapterPosition()
            notifyDataSetChanged()
            itemClick(item.optionDetailId)
        }
        holder.isSelected = selectedPosition == holder.adapterPosition
        holder.onBind(item)
    }

    fun addList(newItems: List<OptionDetailModel>) {
        val currentItems = currentList.toMutableList()
        currentItems.addAll(newItems)
        submitList(currentItems)
    }

    companion object {
        private val diffUtil =
            ItemDiffCallback<OptionDetailModel>(
                onItemsTheSame = { old, new -> old.optionDetailId == new.optionDetailId },
                onContentsTheSame = { old, new -> old == new },
            )
    }
}
