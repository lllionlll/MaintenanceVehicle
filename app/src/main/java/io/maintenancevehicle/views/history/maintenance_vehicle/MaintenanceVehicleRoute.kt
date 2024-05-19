package io.maintenancevehicle.views.history.maintenance_vehicle

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

object MaintenanceVehicleRoute {

    fun backScreen(fragment: Fragment) {
        fragment.findNavController().popBackStack()
    }

    fun goToQrCodeScanner(fragment: Fragment) {
        val action =
            MaintenanceVehicleFragmentDirections.actionMaintenanceVehicleListFragmentToQrCodeScannerFragment(
                isBackToMenu = true
            )
        fragment.findNavController().navigate(action)
    }

}