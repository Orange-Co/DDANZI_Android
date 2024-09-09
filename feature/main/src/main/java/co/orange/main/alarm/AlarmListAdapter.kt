package co.orange.main.alarm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import co.orange.core.util.ItemDiffCallback
import co.orange.domain.entity.response.AlarmItemModel
import co.orange.main.databinding.ItemAlarmBinding

class AlarmListAdapter(
    private val itemClick: (AlarmItemModel) -> (Unit),
) : ListAdapter<AlarmItemModel, AlarmListViewHolder>(diffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): AlarmListViewHolder {
        val inflater by lazy { LayoutInflater.from(parent.context) }
        val binding: ItemAlarmBinding =
            ItemAlarmBinding.inflate(inflater, parent, false)
        return AlarmListViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(
        holder: AlarmListViewHolder,
        position: Int,
    ) {
        val item = getItem(position) ?: return
        holder.onBind(item)
    }

    companion object {
        private val diffUtil =
            ItemDiffCallback<AlarmItemModel>(
                onItemsTheSame = { old, new -> old.orderId == new.orderId },
                onContentsTheSame = { old, new -> old == new },
            )
    }
}
