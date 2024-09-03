package co.orange.presentation.bank.type

import androidx.recyclerview.widget.RecyclerView
import co.orange.core.extension.setOnSingleClickListener
import co.orange.domain.enums.BankType
import kr.genti.presentation.databinding.ItemBankTypeBinding

class BankTypeViewHolder(
    val binding: ItemBankTypeBinding,
    private val itemClick: (BankType) -> (Unit),
) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: BankType) {
        with(binding) {
            tvBankTypeTitle.text = item.displayName
            root.setOnSingleClickListener {
                itemClick(item)
            }
        }
    }
}
