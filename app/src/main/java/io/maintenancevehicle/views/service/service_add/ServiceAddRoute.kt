package io.maintenancevehicle.views.service.service_add

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

object ServiceAddRoute {

    fun backScreen(fragment: Fragment) {
        fragment.findNavController().popBackStack()
    }
}