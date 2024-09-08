package co.orange.sell.onboarding

import androidx.recyclerview.widget.RecyclerView
import co.orange.core.R
import co.orange.sell.databinding.ItemOnboardingGuideBinding
import coil.load

class GuideViewHolder(
    val binding: ItemOnboardingGuideBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(position: Int) {
        when (position) {
            0 -> binding.ivOnboardingGuide.load(R.drawable.img_landing_first)
            1 -> binding.ivOnboardingGuide.load(R.drawable.img_landing_second)
            2 -> binding.ivOnboardingGuide.load(R.drawable.img_landing_third)
        }
    }
}
