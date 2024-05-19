package io.maintenancevehicle.views.vehicle.vehicle_list

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.maintenancevehicle.views.service.service_maintenance.ServiceMaintenanceFragmentDirections

object VehicleListRoute {

    fun backScreen(fragment: Fragment) {
        fragment.findNavController().popBackStack()
    }

    fun goToVehicleAdd(fragment: Fragment) {
        val action =
            VehicleListFragmentDirections.actionVehicleListFragmentToVehicleAddFragment()
        fragment.findNavController().navigate(action)
    }
    fun goToVehicleDetail(fragment: Fragment, vehicleId: String) {
        val action =
            VehicleListFragmentDirections.actionVehicleListFragmentToVehicleDetailFragment(
                vehicleId = vehicleId
            )
        fragment.findNavController().navigate(action)
    }

}