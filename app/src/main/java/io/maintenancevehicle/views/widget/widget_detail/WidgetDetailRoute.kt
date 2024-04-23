package io.maintenancevehicle.views.widget.widget_detail

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

object WidgetDetailRoute {

    fun backScreen(fragment: Fragment) {
        fragment.findNavController().popBackStack()
    }

}