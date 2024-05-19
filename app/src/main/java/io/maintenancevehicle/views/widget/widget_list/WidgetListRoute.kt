package io.maintenancevehicle.views.widget.widget_list

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.maintenancevehicle.views.service.service_maintenance.ServiceMaintenanceFragmentDirections

object WidgetListRoute {

    fun backScreen(fragment: Fragment) {
        fragment.findNavController().popBackStack()
    }

    fun goToWidgetAdd(fragment: Fragment) {
        val action =
            WidgetListFragmentDirections.actionWidgetListFragmentToWidgetAddFragment()
        fragment.findNavController().navigate(action)
    }

    fun goToWidgetDetail(fragment: Fragment, widgetId: String) {
        val action =
            WidgetListFragmentDirections.actionWidgetListFragmentToWidgetDetailFragment(
                widgetId = widgetId
            )
        fragment.findNavController().navigate(action)
    }

}