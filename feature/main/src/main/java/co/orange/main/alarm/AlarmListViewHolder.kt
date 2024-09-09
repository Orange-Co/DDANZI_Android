package co.orange.main.alarm

import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import co.orange.core.R
import co.orange.core.extension.setOnSingleClickListener
import co.orange.domain.entity.response.AlarmListModel.AlarmItemModel
import co.orange.main.databinding.ItemAlarmBinding

class AlarmListViewHolder(
    val binding: ItemAlarmBinding,
    val itemClick: (AlarmItemModel) -> (Unit),
) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: AlarmItemModel) {
        with(binding) {
            tvAlarmItemTitle.text = item.title
            tvAlarmItemBody.text = item.content
            tvAlarmItemDate.text = item.time

            root.setOnSingleClickListener {
                itemClick(item)
            }

            root.background =
                if (item.isChecked) {
                    ColorDrawable(ContextCompat.getColor(itemView.context, R.color.white))
                } else {
                    ColorDrawable(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.emphasize_yellow,
                        ),
                    )
                }
        }
    }
}
