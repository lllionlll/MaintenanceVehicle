package io.maintenancevehicle.views.schedule_view.schedule

import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.databinding.FragmentScheduleBinding

@AndroidEntryPoint
class ScheduleFragment : BaseFragment<FragmentScheduleBinding>(
    FragmentScheduleBinding::inflate
) {

    override fun handleEvent() {
        super.handleEvent()
        binding.btnBack.setOnClickListener {
            ScheduleRoute.backScreen(this)
        }

        binding.scheduleAdd.setOnClickListener {
            ScheduleRoute.goToScheduleAdd(this)
        }
    }
}