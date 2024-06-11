package co.orange.presentation.main.home

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import kr.genti.core.base.BaseFragment
import kr.genti.presentation.R
import kr.genti.presentation.databinding.FragmentHomeBinding

@AndroidEntryPoint
class HomeFragment() : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

    }
}