package co.orange.sell.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.orange.sell.databinding.ItemOnboardingGuideBinding

class GuideAdapter : RecyclerView.Adapter<GuideViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): GuideViewHolder {
        val inflater by lazy { LayoutInflater.from(parent.context) }
        val binding = ItemOnboardingGuideBinding.inflate(inflater, parent, false)
        return GuideViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: GuideViewHolder,
        position: Int,
    ) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int = TOTAL_VIEW_COUNT

    companion object {
        const val TOTAL_VIEW_COUNT = 3
    }
}
