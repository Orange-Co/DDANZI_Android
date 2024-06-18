package co.orange.presentation.search

import androidx.recyclerview.widget.RecyclerView
import kr.genti.core.extension.setOnSingleClickListener
import kr.genti.presentation.databinding.ItemSearchWordBinding

class SearchWordViewHolder(
    val binding: ItemSearchWordBinding,
    val keywordClick: (String) -> (Unit),
) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: String) {
        binding.root.setOnSingleClickListener {
            keywordClick(item)
        }
    }
}
