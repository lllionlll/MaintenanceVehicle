package io.maintenancevehicle.views.service.service_detail

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.maintenancevehicle.views.customer.customer_detail.CustomerDetailFragmentDirections

object ServiceDetailRoute {

    fun backScreen(fragment: Fragment) {
        fragment.findNavController().popBackStack()
    }

    fun goToServiceEdit(fragment: Fragment, serviceId: String) {
        val action = ServiceDetailFragmentDirections.actionServiceDetailFragmentToServiceEditFragment(
            serviceId = serviceId
        )
        fragment.findNavController().navigate(action)
    }

}