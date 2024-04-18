package io.maintenancevehicle.views.menu

import android.view.Menu
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.databinding.FragmentMenuBinding

class MenuFragment : BaseFragment<FragmentMenuBinding>(
    FragmentMenuBinding::inflate
) {

    override fun handleEvent() {
        super.handleEvent()

        binding.clientManagement.setOnClickListener {
            MenuRoute.goToClientManagement(this)
        }

        binding.historyMaintenanceVehicleFragment.setOnClickListener {
            MenuRoute.goToHistoryMaintenanceVehicle(this)
        }

        binding.serviceMaintenance.setOnClickListener {
            MenuRoute.goToServiceMaintenance(this)
        }

        binding.scheduleMaintenance.setOnClickListener {
            MenuRoute.goToScheduleMaintenance(this)
        }

        binding.serviceMaintenance.setOnClickListener {
            MenuRoute.goToServiceMaintenance(this)
        }
    }
}