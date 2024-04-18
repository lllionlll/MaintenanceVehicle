package io.maintenancevehicle.views.customer.customer_add

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

object CustomerAddRoute {

    fun backScreen(fragment: Fragment) {
        fragment.findNavController().popBackStack()
    }
}