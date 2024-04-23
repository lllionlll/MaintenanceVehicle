package io.maintenancevehicle.views.widget.widget_edit

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

object WidgetEditRoute {

    fun backScreen(fragment: Fragment) {
        fragment.findNavController().popBackStack()
    }

}