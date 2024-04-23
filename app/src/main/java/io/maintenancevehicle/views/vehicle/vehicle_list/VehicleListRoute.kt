package io.maintenancevehicle.views.vehicle.vehicle_list

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

object VehicleListRoute {

    fun backScreen(fragment: Fragment) {
        fragment.findNavController().popBackStack()
    }

}