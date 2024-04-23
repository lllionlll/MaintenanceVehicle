package io.maintenancevehicle.views.widget.widget_list

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

object WidgetListRoute {

    fun backScreen(fragment: Fragment) {
        fragment.findNavController().popBackStack()
    }

}