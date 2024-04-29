package io.maintenancevehicle.views.customer.customer_detail

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import io.maintenancevehicle.R

object CustomerDetailRoute {

    fun backScreen(fragment: Fragment) {
        fragment.findNavController().popBackStack()
    }

    fun goToCustomerEdit(fragment: Fragment, id: String) {
        fragment.findNavController().navigate(R.id.action_customerDetailFragment_to_customerEditFragment, bundleOf("id"  to id))
    }

    fun goToImagePreview(fragment: Fragment, customerId: String) {
        val action =
            CustomerDetailFragmentDirections.actionCustomerDetailFragmentToImagePreViewFragment(
                customerId = customerId
            )
        fragment.findNavController().navigate(action)
    }
}