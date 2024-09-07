package co.orange.presentation.sell.onboarding

import androidx.recyclerview.widget.RecyclerView
import coil.load
import kr.genti.presentation.R
import kr.genti.presentation.databinding.ItemOnboardingGuideBinding

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
