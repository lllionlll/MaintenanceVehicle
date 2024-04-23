package io.maintenancevehicle.views.widget.widget_detail

import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.databinding.FragmentWidgetDetailBinding

@AndroidEntryPoint
class WidgetDetailFragment : BaseFragment<FragmentWidgetDetailBinding>(
    FragmentWidgetDetailBinding::inflate
) {

    override fun handleEvent() {
        super.handleEvent()
        binding.btnBack.setOnClickListener {
            WidgetDetailRoute.backScreen(this)
        }
    }

}