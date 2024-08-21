package co.orange.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import co.orange.core.util.ItemDiffCallback
import co.orange.domain.entity.response.ProductModel
import co.orange.presentation.main.home.HomeAdapter
import kr.genti.presentation.databinding.ItemSearchProductBinding

class SearchItemAdapter(
    private val itemClick: (ProductModel) -> (Unit),
    private val likeClick: (String, Boolean, Int) -> (Unit),
) : ListAdapter<ProductModel, SearchItemViewHolder>(diffUtil) {
    private var itemList = mutableListOf<ProductModel>()

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
        holder.onBind(itemList[position], position)
    }

    fun setItemList(itemList: List<ProductModel>) {
        this.itemList = itemList.toMutableList()
        notifyDataSetChanged()
    }

    fun plusItemLike(position: Int) {
        itemList[position].isInterested = true
        itemList[position].interestCount += 1
        notifyItemChanged(position + HomeAdapter.HEADER_COUNT)
    }

    fun minusItemLike(position: Int) {
        itemList[position].isInterested = false
        itemList[position].interestCount -= 1
        notifyItemChanged(position + HomeAdapter.HEADER_COUNT)
    }

    companion object {
        private val diffUtil =
            ItemDiffCallback<ProductModel>(
                onItemsTheSame = { old, new -> old.productId == new.productId },
                onContentsTheSame = { old, new -> old == new },
            )
    }
}
