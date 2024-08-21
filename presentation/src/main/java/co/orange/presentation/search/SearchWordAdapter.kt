package co.orange.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import co.orange.core.util.ItemDiffCallback
import kr.genti.presentation.databinding.ItemSearchWordBinding

class SearchWordAdapter(
    private val keywordClick: (String) -> (Unit),
) : ListAdapter<String, SearchWordViewHolder>(diffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SearchWordViewHolder {
        val inflater by lazy { LayoutInflater.from(parent.context) }
        val binding: ItemSearchWordBinding =
            ItemSearchWordBinding.inflate(inflater, parent, false)
        return SearchWordViewHolder(binding, keywordClick)
    }

    override fun onBindViewHolder(
        holder: SearchWordViewHolder,
        position: Int,
    ) {
        val item = getItem(position) ?: return
        holder.onBind(item)
    }

    companion object {
        private val diffUtil =
            ItemDiffCallback<String>(
                onItemsTheSame = { old, new -> old.length == new.length },
                onContentsTheSame = { old, new -> old == new },
            )
    }
}
