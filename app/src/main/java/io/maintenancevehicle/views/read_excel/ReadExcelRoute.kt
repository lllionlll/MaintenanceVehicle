package io.maintenancevehicle.views.read_excel

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

object ReadExcelRoute {

    fun backScreen(fragment: Fragment) {
        fragment.findNavController().popBackStack()
    }
}