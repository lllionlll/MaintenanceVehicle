package io.maintenancevehicle.views.vehicle.vehicle_edit

import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.databinding.FragmentVehicleEditBinding

@AndroidEntryPoint
class VehicleEditFragment : BaseFragment<FragmentVehicleEditBinding>(
    FragmentVehicleEditBinding::inflate
) {

    override fun handleEvent() {
        super.handleEvent()
        binding.btnBack.setOnClickListener {
            VehicleEditRoute.backScreen(this)
        }
    }

}