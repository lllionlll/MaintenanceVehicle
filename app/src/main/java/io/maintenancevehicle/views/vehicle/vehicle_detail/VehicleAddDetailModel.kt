package io.maintenancevehicle.views.vehicle.vehicle_detail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.maintenancevehicle.data.repository.MaintenanceVehicleRepository
import javax.inject.Inject

@HiltViewModel
class VehicleAddDetailModel @Inject constructor(
    private val maintenanceVehicleRepository: MaintenanceVehicleRepository
): ViewModel() {

}