package io.maintenancevehicle.views.image_preview

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

object ImagePreViewRoute {

    fun backScreen(fragment: Fragment) {
        fragment.findNavController().popBackStack()
    }
}