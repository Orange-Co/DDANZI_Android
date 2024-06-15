package co.orange.presentation.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import co.orange.domain.entity.response.OptionModel.OptionDetailModel
import kr.genti.core.util.ItemDiffCallback
import kr.genti.presentation.databinding.ItemOptionDetailBinding

class OptionDetailAdapter(
    private val itemClick: (Int, Long) -> Unit,
) : ListAdapter<OptionDetailModel, OptionDetailViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionDetailViewHolder {
        val inflater by lazy { LayoutInflater.from(parent.context) }
        val binding: ItemOptionDetailBinding =
            ItemOptionDetailBinding.inflate(inflater, parent, false)
        return OptionDetailViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: OptionDetailViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.onBind(item, position)
    }

    fun addList(newItems: List<OptionDetailModel>) {
        val currentItems = currentList.toMutableList()
        currentItems.addAll(newItems)
        submitList(currentItems)
    }

    companion object {
        private val diffUtil = ItemDiffCallback<OptionDetailModel>(
            onItemsTheSame = { old, new -> old.optionDetailId == new.optionDetailId },
            onContentsTheSame = { old, new -> old == new },
        )
    }
}