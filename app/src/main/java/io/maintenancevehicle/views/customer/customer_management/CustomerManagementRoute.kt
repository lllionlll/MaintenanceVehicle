package io.maintenancevehicle.views.customer.customer_management

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

object CustomerManagementRoute {

    fun backScreen(fragment: Fragment) {
        fragment.findNavController().popBackStack()
    }

    fun goToCustomerDetail(fragment: Fragment, customerId: String) {
        val action =
            CustomerManagementFragmentDirections.actionCustomerManagementFragmentToCustomerDetailFragment(
                customerId = customerId
            )
        fragment.findNavController().navigate(action)
    }

    fun goToCustomerAdd(fragment: Fragment) {
        val action =
            CustomerManagementFragmentDirections.actionCustomerManagementFragmentToCustomerAddFragment()
        fragment.findNavController().navigate(action)
    }
}