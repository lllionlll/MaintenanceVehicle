package io.maintenancevehicle.views.statistical

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

object StatisticalRoute {

    fun backScreen(fragment: Fragment) {
        fragment.findNavController().popBackStack()
    }
}