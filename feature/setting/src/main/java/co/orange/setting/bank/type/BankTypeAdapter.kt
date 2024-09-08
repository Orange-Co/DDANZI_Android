package co.orange.setting.bank.type

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import co.orange.core.util.ItemDiffCallback
import co.orange.domain.enums.BankType
import co.orange.setting.databinding.ItemBankTypeBinding

class BankTypeAdapter(
    private val itemClick: (BankType) -> (Unit),
) : ListAdapter<BankType, BankTypeViewHolder>(diffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BankTypeViewHolder {
        val inflater by lazy { LayoutInflater.from(parent.context) }
        val binding: ItemBankTypeBinding =
            ItemBankTypeBinding.inflate(inflater, parent, false)
        return BankTypeViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(
        holder: BankTypeViewHolder,
        position: Int,
    ) {
        val item = getItem(position) ?: return
        holder.onBind(item)
    }

    companion object {
        private val diffUtil =
            ItemDiffCallback<BankType>(
                onItemsTheSame = { old, new -> old.code == new.code },
                onContentsTheSame = { old, new -> old == new },
            )
    }
}
