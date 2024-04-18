package io.maintenancevehicle.views.schedule_view.schedule_detail

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

object ScheduleDetailRoute {

    fun backScreen(fragment: Fragment) {
        fragment.findNavController().popBackStack()
    }

    fun goToScheduleEdit(fragment: Fragment) {
        val action =
            ScheduleDetailFragmentDirections.actionScheduleDetailFragmentToScheduleEditFragment()
        fragment.findNavController().navigate(action)
    }
}