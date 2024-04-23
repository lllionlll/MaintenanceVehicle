package io.maintenancevehicle.views.vehicle.vehicle_add

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

object VehicleAddRoute {

    fun backScreen(fragment: Fragment) {
        fragment.findNavController().popBackStack()
    }

}