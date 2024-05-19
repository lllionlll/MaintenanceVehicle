package io.maintenancevehicle.views.qr_code_scanner

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.maintenancevehicle.R

object QrCodeScannerRoute {

    fun backScreen(fragment: Fragment) {
        fragment.findNavController().popBackStack()
    }

    fun backToMenu(fragment: Fragment) {
        fragment.findNavController().popBackStack(R.id.menuFragment, false)
    }
}