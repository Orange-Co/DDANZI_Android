package co.orange.presentation.detail

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import co.orange.domain.entity.response.ProductOptionModel
import kr.genti.presentation.databinding.ItemOptionBinding

class OptionViewHolder(
    val binding: ItemOptionBinding,
    private val itemClick: (Int, Long) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    var selectedPosition: Int = -1

    fun onBind(
        item: ProductOptionModel,
        position: Int,
    ) {
        selectedPosition = position
        with(binding) {
            tvOptionItemTitle.text = item.type
            tvOptionItemTitle.setOnClickListener {
                rvOptionDetail.isVisible = !rvOptionDetail.isVisible
                btnToggle.isSelected = !btnToggle.isSelected
            }
            rvOptionDetail.adapter =
                OptionDetailAdapter(
                    itemClick = ::initItemClickListener,
                ).apply {
                    addList(item.optionDetailList)
                }
        }
    }

    private fun initItemClickListener(optionDetailId: Long) {
        itemClick(selectedPosition, optionDetailId)
    }
}
