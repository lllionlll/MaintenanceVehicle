package io.maintenancevehicle.views.widget.widget_add

import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.databinding.FragmentWidgetAddBinding

@AndroidEntryPoint
class WidgetAddFragment : BaseFragment<FragmentWidgetAddBinding>(
    FragmentWidgetAddBinding::inflate
) {

    override fun handleEvent() {
        super.handleEvent()
        binding.btnBack.setOnClickListener {
            WidgetAddRoute.backScreen(this)
        }
    }

}