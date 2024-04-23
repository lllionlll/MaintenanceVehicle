package io.maintenancevehicle.views.statistical

import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.databinding.FragmentStatisticalBinding

@AndroidEntryPoint
class StatisticalFragment : BaseFragment<FragmentStatisticalBinding>(
    FragmentStatisticalBinding::inflate
) {

    override fun handleEvent() {
        super.handleEvent()

        binding.btnBack.setOnClickListener {
            StatisticalRoute.backScreen(this)
        }
    }

}