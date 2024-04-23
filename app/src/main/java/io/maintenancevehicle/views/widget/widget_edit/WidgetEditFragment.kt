package io.maintenancevehicle.views.widget.widget_edit

import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.databinding.FragmentWidgetEditBinding

@AndroidEntryPoint
class WidgetEditFragment : BaseFragment<FragmentWidgetEditBinding>(
    FragmentWidgetEditBinding::inflate
) {

    override fun handleEvent() {
        super.handleEvent()
        binding.btnBack.setOnClickListener {
            WidgetEditRoute.backScreen(this)
        }
    }

}