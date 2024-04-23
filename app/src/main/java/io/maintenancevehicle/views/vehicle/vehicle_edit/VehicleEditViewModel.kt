package io.maintenancevehicle.views.vehicle.vehicle_edit

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.maintenancevehicle.data.repository.MaintenanceVehicleRepository
import javax.inject.Inject

@HiltViewModel
class VehicleEditViewModel @Inject constructor(
    private val maintenanceVehicleRepository: MaintenanceVehicleRepository
): ViewModel() {

}