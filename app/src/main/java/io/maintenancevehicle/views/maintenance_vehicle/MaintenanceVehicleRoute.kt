package io.maintenancevehicle.views.maintenance_vehicle

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

object MaintenanceVehicleRoute {

    fun backScreen(fragment: Fragment) {
        fragment.findNavController().popBackStack()
    }

}