package io.maintenancevehicle.views.customer.customer_edit

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

object CustomerEditRoute {

    fun backScreen(fragment: Fragment) {
        fragment.findNavController().popBackStack()
    }
}