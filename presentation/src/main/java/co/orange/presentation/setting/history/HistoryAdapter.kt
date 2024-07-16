package co.orange.presentation.setting.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import co.orange.core.util.ItemDiffCallback
import co.orange.domain.entity.response.ProductModel
import kr.genti.presentation.databinding.ItemHomeProductBinding

class HistoryAdapter(
    private val itemClick: (ProductModel) -> (Unit),
) : ListAdapter<ProductModel, HistoryViewHolder>(diffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HistoryViewHolder {
        val inflater by lazy { LayoutInflater.from(parent.context) }
        val binding: ItemHomeProductBinding =
            ItemHomeProductBinding.inflate(inflater, parent, false)
        return HistoryViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(
        holder: HistoryViewHolder,
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
