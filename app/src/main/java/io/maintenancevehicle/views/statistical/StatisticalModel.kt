package io.maintenancevehicle.views.statistical

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.maintenancevehicle.data.repository.MaintenanceVehicleRepository
import javax.inject.Inject

@HiltViewModel
class StatisticalModel @Inject constructor(
    private val maintenanceVehicleRepository: MaintenanceVehicleRepository
): ViewModel() {

}