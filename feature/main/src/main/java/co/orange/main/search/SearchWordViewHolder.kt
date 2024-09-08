package co.orange.main.search

import androidx.recyclerview.widget.RecyclerView
import co.orange.core.extension.setOnSingleClickListener
import co.orange.main.databinding.ItemSearchWordBinding

class SearchWordViewHolder(
    val binding: ItemSearchWordBinding,
    val keywordClick: (String) -> (Unit),
) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: String) {
        with(binding) {
            tvSearchWord.text = item
            root.setOnSingleClickListener {
                keywordClick(item)
            }
        }
    }
}
