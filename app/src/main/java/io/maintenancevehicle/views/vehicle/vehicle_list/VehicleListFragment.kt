package io.maintenancevehicle.views.vehicle.vehicle_list

import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.databinding.FragmentVehicleListBinding

@AndroidEntryPoint
class VehicleListFragment : BaseFragment<FragmentVehicleListBinding>(
    FragmentVehicleListBinding::inflate
) {

    override fun handleEvent() {
        super.handleEvent()
        binding.btnBack.setOnClickListener {
            VehicleListRoute.backScreen(this)
        }
    }
}