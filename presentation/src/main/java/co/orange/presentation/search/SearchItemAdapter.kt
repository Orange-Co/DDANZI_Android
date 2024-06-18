package co.orange.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import co.orange.domain.entity.response.ProductModel
import kr.genti.core.util.ItemDiffCallback
import kr.genti.presentation.databinding.ItemSearchProductBinding

class SearchItemAdapter(
    private val itemClick: (Long) -> (Unit),
) : ListAdapter<ProductModel, SearchItemViewHolder>(diffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SearchItemViewHolder {
        val inflater by lazy { LayoutInflater.from(parent.context) }
        val binding: ItemSearchProductBinding =
            ItemSearchProductBinding.inflate(inflater, parent, false)
        return SearchItemViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(
        holder: SearchItemViewHolder,
        position: Int,
    ) {
        val item = getItem(position) ?: return
        holder.onBind(item)
    }

    fun addList(newItems: List<ProductModel>) {
        val currentItems = currentList.toMutableList()
        currentItems.addAll(newItems)
        submitList(currentItems)
    }

    companion object {
        private val diffUtil =
            ItemDiffCallback<ProductModel>(
                onItemsTheSame = { old, new -> old.productId == new.productId },
                onContentsTheSame = { old, new -> old == new },
            )
    }
}
