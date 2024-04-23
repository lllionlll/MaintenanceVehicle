package io.maintenancevehicle.views.menu

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

object MenuRoute {

    fun goToClientManagement(fragment: Fragment) {
        val action = MenuFragmentDirections.actionMenuFragmentToClientManagementFragment()
        fragment.findNavController().navigate(action)
    }

    fun goToHistoryMaintenanceVehicle(fragment: Fragment) {
        val action = MenuFragmentDirections.actionMenuFragmentToHistoryMaintenanceVehicleFragment()
        fragment.findNavController().navigate(action)
    }

    fun goToServiceMaintenance(fragment: Fragment) {
        val action = MenuFragmentDirections.actionMenuFragmentToServiceMaintenanceFragment()
        fragment.findNavController().navigate(action)
    }

    fun goToScheduleMaintenance(fragment: Fragment) {
        val action = MenuFragmentDirections.actionMenuFragmentToScheduleFragment()
        fragment.findNavController().navigate(action)
    }

    fun goToStatistical(fragment: Fragment) {
        val action = MenuFragmentDirections.actionMenuFragmentToStatisticalFragment()
        fragment.findNavController().navigate(action)
    }

    fun goToMaintenanceVehicle(fragment: Fragment) {
        val action = MenuFragmentDirections.actionMenuFragmentToMaintenanceVehicleListFragment()
        fragment.findNavController().navigate(action)
    }

    fun goToWidgetVehicle(fragment: Fragment) {

    }

    fun goToVehicleList(fragment: Fragment) {
        val action = MenuFragmentDirections.actionMenuFragmentToVehicleListFragment()
        fragment.findNavController().navigate(action)
    }
}