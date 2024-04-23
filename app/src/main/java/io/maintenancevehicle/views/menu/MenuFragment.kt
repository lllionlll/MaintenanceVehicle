package io.maintenancevehicle.views.menu

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

        binding.historyMaintenanceVehicle.setOnClickListener {
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

        binding.scheduleMaintenance.setOnClickListener {
            MenuRoute.goToScheduleMaintenance(this)
        }

        binding.statistical.setOnClickListener {
            MenuRoute.goToStatistical(this)
        }

        binding.maintenanceVehicle.setOnClickListener {
            MenuRoute.goToMaintenanceVehicle(this)
        }

        binding.vehicleList.setOnClickListener {
            MenuRoute.goToVehicleList(this)
        }

        binding.widgetList.setOnClickListener {
            MenuRoute.goToWidgetVehicle(this)
        }
    }
}