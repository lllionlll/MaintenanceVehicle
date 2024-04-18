package io.maintenancevehicle.views.schedule_view.schedule_add

import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.databinding.FragmentScheduleAddBinding

@AndroidEntryPoint
class ScheduleAddFragment : BaseFragment<FragmentScheduleAddBinding>(
    FragmentScheduleAddBinding::inflate
) {

    override fun handleEvent() {
        super.handleEvent()
        binding.btnBack.setOnClickListener {
            ScheduleAddRoute.backScreen(this)
        }
    }

}