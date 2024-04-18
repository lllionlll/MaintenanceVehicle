package io.maintenancevehicle.views.schedule_view.schedule_add

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

object ScheduleAddRoute {

    fun backScreen(fragment: Fragment) {
        fragment.findNavController().popBackStack()
    }
}