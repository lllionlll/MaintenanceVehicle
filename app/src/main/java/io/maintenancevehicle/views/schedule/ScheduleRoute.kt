package io.maintenancevehicle.views.schedule

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

object ScheduleRoute {

    fun backScreen(fragment: Fragment) {
        fragment.findNavController().popBackStack()
    }

    fun goToQrCodeScanner(fragment: Fragment) {
        val action = ScheduleFragmentDirections.actionScheduleFragmentToQrCodeScannerFragment(
            isBackToMenu = false
        )
        fragment.findNavController().navigate(action)
    }
}