package io.maintenancevehicle.views.widget.widget_list

import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.databinding.FragmentWidgetListBinding
import io.maintenancevehicle.views.widget.widget_edit.WidgetEditRoute

@AndroidEntryPoint
class WidgetListFragment : BaseFragment<FragmentWidgetListBinding>(
    FragmentWidgetListBinding::inflate
) {

    override fun handleEvent() {
        super.handleEvent()
        binding.btnBack.setOnClickListener {
            WidgetEditRoute.backScreen(this)
        }
    }

}