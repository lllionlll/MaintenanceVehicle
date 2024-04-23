package io.maintenancevehicle.views.vehicle.vehicle_detail

import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.databinding.FragmentVehicleDetailBinding
import io.maintenancevehicle.views.service.service_detail.ServiceDetailRoute

@AndroidEntryPoint
class VehicleDetailFragment : BaseFragment<FragmentVehicleDetailBinding>(
    FragmentVehicleDetailBinding::inflate
) {

    override fun handleEvent() {
        super.handleEvent()
        binding.btnBack.setOnClickListener {
            ServiceDetailRoute.backScreen(this)
        }
    }

}