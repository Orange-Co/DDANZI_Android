package co.orange.presentation.main.profile.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import co.orange.core.util.ItemDiffCallback
import co.orange.domain.entity.response.HistoryBuyModel.OrderProductModel
import kr.genti.presentation.databinding.ItemHistoryBuyBinding

class HistoryBuyAdapter(
    private val itemClick: (String) -> (Unit),
) : ListAdapter<OrderProductModel, HistoryBuyViewHolder>(diffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HistoryBuyViewHolder {
        val inflater by lazy { LayoutInflater.from(parent.context) }
        val binding: ItemHistoryBuyBinding =
            ItemHistoryBuyBinding.inflate(inflater, parent, false)
        return HistoryBuyViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(
        holder: HistoryBuyViewHolder,
        position: Int,
    ) {
        val item = getItem(position) ?: return
        holder.onBind(item)
    }

    fun addList(newItems: List<OrderProductModel>) {
        val currentItems = currentList.toMutableList()
        currentItems.addAll(newItems)
        submitList(currentItems)
    }

    companion object {
        private val diffUtil =
            ItemDiffCallback<OrderProductModel>(
                onItemsTheSame = { old, new -> old.productId == new.productId },
                onContentsTheSame = { old, new -> old == new },
            )
    }
}
