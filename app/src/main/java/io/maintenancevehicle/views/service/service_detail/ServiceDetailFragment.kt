package io.maintenancevehicle.views.service.service_detail

import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.databinding.FragmentServiceDetailBinding

@AndroidEntryPoint
class ServiceDetailFragment : BaseFragment<FragmentServiceDetailBinding>(
    FragmentServiceDetailBinding::inflate
) {

    override fun handleEvent() {
        super.handleEvent()
        binding.btnBack.setOnClickListener {
            ServiceDetailRoute.backScreen(this)
        }
    }

}