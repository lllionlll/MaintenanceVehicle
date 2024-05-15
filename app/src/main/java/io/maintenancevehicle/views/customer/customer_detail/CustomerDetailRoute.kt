package io.maintenancevehicle.views.customer.customer_detail

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

object CustomerDetailRoute {

    fun backScreen(fragment: Fragment) {
        fragment.findNavController().popBackStack()
    }

    fun goToCustomerEdit(fragment: Fragment, customerId: String) {
        val action =
            CustomerDetailFragmentDirections.actionCustomerDetailFragmentToCustomerEditFragment(
                customerId = customerId
            )
        fragment.findNavController().navigate(action)
    }

    fun goToImagePreview(fragment: Fragment, customerId: String) {
        val action =
            CustomerDetailFragmentDirections.actionCustomerDetailFragmentToImagePreViewFragment(
                customerId = customerId
            )
        fragment.findNavController().navigate(action)
    }
}