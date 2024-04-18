package io.maintenancevehicle.views.schedule_view.schedule_detail

import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.databinding.FragmentScheduleDetailBinding

@AndroidEntryPoint
class ScheduleDetailFragment : BaseFragment<FragmentScheduleDetailBinding>(
    FragmentScheduleDetailBinding::inflate
) {

    override fun handleEvent() {
        super.handleEvent()
        binding.btnBack.setOnClickListener {
            ScheduleDetailRoute.backScreen(this)
        }

        binding.scheduleEdit.setOnClickListener {
            ScheduleDetailRoute.goToScheduleEdit(this)
        }
    }

}