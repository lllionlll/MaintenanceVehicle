package io.maintenancevehicle.views.vehicle.vehicle_edit

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

object VehicleEditRoute {

    fun backScreen(fragment: Fragment) {
        fragment.findNavController().popBackStack()
    }

}