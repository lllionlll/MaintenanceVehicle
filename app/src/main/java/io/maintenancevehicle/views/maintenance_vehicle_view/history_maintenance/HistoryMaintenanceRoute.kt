package io.maintenancevehicle.views.maintenance_vehicle_view.history_maintenance

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

object HistoryMaintenanceRoute {

    fun backScreen(fragment: Fragment) {
        fragment.findNavController().popBackStack()
    }
}