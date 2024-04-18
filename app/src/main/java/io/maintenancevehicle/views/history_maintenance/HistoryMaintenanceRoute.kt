package io.maintenancevehicle.views.history_maintenance

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

object HistoryMaintenanceRoute {

    fun backScreen(fragment: Fragment) {
        fragment.findNavController().popBackStack()
    }
}