package co.orange.setting.history.interest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import co.orange.core.util.ItemDiffCallback
import co.orange.domain.entity.response.ProductModel
import co.orange.setting.databinding.ItemHistoryItemBinding

class HistoryInterestAdapter(
    private val itemClick: (String) -> (Unit),
) : ListAdapter<ProductModel, HistoryInterestViewHolder>(diffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HistoryInterestViewHolder {
        val inflater by lazy { LayoutInflater.from(parent.context) }
        val binding: ItemHistoryItemBinding =
            ItemHistoryItemBinding.inflate(inflater, parent, false)
        return HistoryInterestViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(
        holder: HistoryInterestViewHolder,
        position: Int,
    ) {
        val item = getItem(position) ?: return
        holder.onBind(item)
    }

    companion object {
        private val diffUtil =
            ItemDiffCallback<ProductModel>(
                onItemsTheSame = { old, new -> old.productId == new.productId },
                onContentsTheSame = { old, new -> old == new },
            )
    }
}
