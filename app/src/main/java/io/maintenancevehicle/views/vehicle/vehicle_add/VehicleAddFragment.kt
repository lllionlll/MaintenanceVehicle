package io.maintenancevehicle.views.vehicle.vehicle_add

import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.databinding.FragmentVehicleAddBinding

@AndroidEntryPoint
class VehicleAddFragment : BaseFragment<FragmentVehicleAddBinding>(
    FragmentVehicleAddBinding::inflate
) {

    override fun handleEvent() {
        super.handleEvent()
        binding.btnBack.setOnClickListener {
            VehicleAddRoute.backScreen(this)
        }
    }

}