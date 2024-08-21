package co.orange.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import co.orange.core.util.ItemDiffCallback
import co.orange.domain.entity.response.ProductModel
import kr.genti.presentation.databinding.ItemSearchProductBinding

class SearchItemAdapter(
    private val itemClick: (ProductModel) -> Unit,
    private val likeClick: (String, Boolean, Int) -> Unit,
) : ListAdapter<ProductModel, SearchItemViewHolder>(diffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SearchItemViewHolder {
        val inflater by lazy { LayoutInflater.from(parent.context) }
        val binding: ItemSearchProductBinding =
            ItemSearchProductBinding.inflate(inflater, parent, false)
        return SearchItemViewHolder(binding, itemClick, likeClick)
    }

    override fun onBindViewHolder(
        holder: SearchItemViewHolder,
        position: Int,
    ) {
        val item = getItem(position) ?: return
        holder.onBind(item, position)
    }

    fun plusItemLike(position: Int) {
        currentList.toMutableList()[position].apply {
            isInterested = true
            interestCount += 1
        }
        submitList(currentList)
        notifyItemChanged(position)
    }

    fun minusItemLike(position: Int) {
        currentList.toMutableList()[position].apply {
            isInterested = false
            interestCount -= 1
        }
        submitList(currentList)
        notifyItemChanged(position)
    }

    companion object {
        private val diffUtil =
            ItemDiffCallback<ProductModel>(
                onItemsTheSame = { old, new -> old.productId == new.productId },
                onContentsTheSame = { old, new -> old == new },
            )
    }
}
