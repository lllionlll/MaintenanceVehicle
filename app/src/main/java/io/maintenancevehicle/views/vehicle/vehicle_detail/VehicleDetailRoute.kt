package io.maintenancevehicle.views.vehicle.vehicle_detail

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

object VehicleDetailRoute {

    fun backScreen(fragment: Fragment) {
        fragment.findNavController().popBackStack()
    }

}