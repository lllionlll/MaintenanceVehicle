package io.maintenancevehicle.views.service.service_maintenance

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

object ServiceMaintenanceRoute {

    fun backScreen(fragment: Fragment) {
        fragment.findNavController().popBackStack()
    }

    fun goToServiceAdd(fragment: Fragment) {
        val action =
            ServiceMaintenanceFragmentDirections.actionServiceMaintenanceFragmentToServiceAddFragment()
        fragment.findNavController().navigate(action)
    }
    fun goToServiceDetail(fragment: Fragment, serviceId: String) {
        val action =
            ServiceMaintenanceFragmentDirections.actionServiceMaintenanceFragmentToServiceDetailFragment(
                serviceId = serviceId
            )
        fragment.findNavController().navigate(action)
    }
}