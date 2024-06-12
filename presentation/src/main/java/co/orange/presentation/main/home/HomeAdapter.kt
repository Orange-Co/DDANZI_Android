package co.orange.presentation.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.orange.domain.entity.response.ProductModel
import kr.genti.core.util.ItemDiffCallback
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ItemHomeBannerBinding
import kr.genti.presentation.databinding.ItemHomeProductBinding

class HomeAdapter(
    private val bannerClick: (Unit) -> (Unit),
    private val productClick: (Unit) -> (Unit),
    private val likeClick: (Unit) -> (Unit),
) : ListAdapter<ProductModel, RecyclerView.ViewHolder>(diffUtil) {
    private var itemList = mutableListOf<ProductModel>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        val inflater by lazy { LayoutInflater.from(parent.context) }

        return when (viewType) {
            VIEW_TYPE_BANNER ->
                HomeBannerViewHolder(
                    ItemHomeBannerBinding.inflate(inflater, parent, false),
                    bannerClick
                )

            VIEW_TYPE_PRODUCT ->
                HomeProductViewHolder(
                    ItemHomeProductBinding.inflate(inflater, parent, false),
                    productClick,
                    likeClick,
                )

            else -> throw ClassCastException(
                parent.context.getString(
                    R.string.view_type_error_msg,
                    viewType,
                ),
            )
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is HomeBannerViewHolder -> {
                holder.onBind()
            }

            is HomeProductViewHolder -> {
                val itemPosition = position - HEADER_COUNT
                holder.onBind(itemList[itemPosition])
            }
        }
        val layoutParams = holder.itemView.layoutParams as RecyclerView.LayoutParams
        layoutParams.bottomMargin = if (position == itemList.size) 24 else 0
        holder.itemView.layoutParams = layoutParams
    }

    override fun getItemCount() = itemList.size + HEADER_COUNT

    override fun getItemViewType(position: Int) =
        when (position) {
            0 -> VIEW_TYPE_BANNER
            else -> VIEW_TYPE_PRODUCT
        }

    fun addItemList(newItems: List<ProductModel>) {
        this.itemList.addAll(newItems)
        notifyDataSetChanged()
    }

    fun setItemList(itemList: List<ProductModel>) {
        this.itemList = itemList.toMutableList()
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        if (this.itemList.isNotEmpty()) {
            this.itemList.removeAt(position)
            notifyItemRemoved(position + HEADER_COUNT)
            notifyItemRangeChanged(position + HEADER_COUNT, itemCount)
        }
    }

    companion object {
        private val diffUtil =
            ItemDiffCallback<ProductModel>(
                onItemsTheSame = { old, new -> old.productId == new.productId },
                onContentsTheSame = { old, new -> old == new },
            )

        const val HEADER_COUNT = 1

        const val VIEW_TYPE_BANNER = 0
        const val VIEW_TYPE_PRODUCT = 1
    }
}
