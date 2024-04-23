package io.maintenancevehicle.views.widget.widget_add

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.maintenancevehicle.data.repository.MaintenanceVehicleRepository
import javax.inject.Inject

@HiltViewModel
class WidgetAddViewModel @Inject constructor(
    private val maintenanceVehicleRepository: MaintenanceVehicleRepository
): ViewModel() {

}