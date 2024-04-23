package io.maintenancevehicle.views.widget.widget_add

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

object WidgetAddRoute {

    fun backScreen(fragment: Fragment) {
        fragment.findNavController().popBackStack()
    }

}