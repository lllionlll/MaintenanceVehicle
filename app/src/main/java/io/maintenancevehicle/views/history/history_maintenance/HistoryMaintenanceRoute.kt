package io.maintenancevehicle.views.history.history_maintenance

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

object HistoryMaintenanceRoute {

    fun backScreen(fragment: Fragment) {
        fragment.findNavController().popBackStack()
    }

    fun goToQrCodeScanner(fragment: Fragment) {
//        val action = HistoryMaintenanceFragmentDirections.actionHistoryMaintenanceDeviceFragmentToQrCodeScannerFragment(
//            isBackToMenu = false
//        )
//        fragment.findNavController().navigate(action)
    }
}