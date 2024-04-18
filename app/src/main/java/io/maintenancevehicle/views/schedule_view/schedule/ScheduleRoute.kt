package io.maintenancevehicle.views.schedule_view.schedule

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

object ScheduleRoute {

    fun backScreen(fragment: Fragment) {
        fragment.findNavController().popBackStack()
    }

    fun goToScheduleAdd(fragment: Fragment) {
        val action = ScheduleFragmentDirections.actionScheduleFragmentToScheduleAddFragment()
        fragment.findNavController().navigate(action)
    }

    fun goToScheduleDetail(fragment: Fragment) {
        val action = ScheduleFragmentDirections.actionScheduleFragmentToScheduleDetailFragment()
        fragment.findNavController().navigate(action)
    }
}