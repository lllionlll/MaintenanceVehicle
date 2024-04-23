package io.maintenancevehicle.views.service.service_edit

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

object ServiceEditRoute {

    fun backScreen(fragment: Fragment) {
        fragment.findNavController().popBackStack()
    }

}