package io.maintenancevehicle.views.vehicle.vehicle_detail

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.maintenancevehicle.views.service.service_detail.ServiceDetailFragmentDirections

object VehicleDetailRoute {

    fun backScreen(fragment: Fragment) {
        fragment.findNavController().popBackStack()
    }

    fun goToVehicleEdit(fragment: Fragment, vehicleId: String) {
        val action = VehicleDetailFragmentDirections.actionVehicleDetailFragmentToVehicleEditFragment(
            vehicleId = vehicleId
        )
        fragment.findNavController().navigate(action)
    }

}