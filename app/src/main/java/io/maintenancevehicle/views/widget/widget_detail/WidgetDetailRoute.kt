package io.maintenancevehicle.views.widget.widget_detail

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.maintenancevehicle.views.vehicle.vehicle_detail.VehicleDetailFragmentDirections

object WidgetDetailRoute {

    fun backScreen(fragment: Fragment) {
        fragment.findNavController().popBackStack()
    }

    fun goToWidgetEdit(fragment: Fragment, widgetId: String) {
        val action = WidgetDetailFragmentDirections.actionWidgetDetailFragmentToWidgetEditFragment(
            widgetId = widgetId
        )
        fragment.findNavController().navigate(action)
    }


}