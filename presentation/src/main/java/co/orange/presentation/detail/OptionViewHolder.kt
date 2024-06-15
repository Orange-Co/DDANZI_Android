package co.orange.presentation.detail

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import co.orange.domain.entity.response.OptionModel
import kr.genti.core.extension.setOnSingleClickListener
import kr.genti.presentation.databinding.ItemOptionBinding

class OptionViewHolder(
    val binding: ItemOptionBinding,
    private val itemClick: (Int, Long, Long) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    var selectedItem: OptionModel? = null

    fun onBind(item: OptionModel, position: Int) {
        // TODO: 수정된 UI 대응
        selectedItem = item
        with(binding) {
            tvOptionItemTitle.text = item.type
            tvOptionItemTitle.setOnClickListener {
                rvOptionDetail.isVisible = !rvOptionDetail.isVisible
                btnToggle.isSelected = !btnToggle.isSelected
            }
            rvOptionDetail.adapter = OptionDetailAdapter(
                itemClick = ::initItemClickListener
            ).apply {
                addList(item.optionDetailList)
            }
        }
    }

    private fun initItemClickListener(position: Int, optionDetailId: Long) {
        itemClick(position, selectedItem?.optionId ?: -1, optionDetailId)
    }

}