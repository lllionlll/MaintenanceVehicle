package io.maintenancevehicle.views.service.service_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.maintenancevehicle.data.model.Service
import io.maintenancevehicle.data.repository.MaintenanceVehicleRepository
import io.maintenancevehicle.data.DataResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ServiceEditViewModel @Inject constructor(
    private val maintenanceVehicleRepository: MaintenanceVehicleRepository
) : ViewModel() {

    fun editService(service: Service) {
        viewModelScope.launch {
            try {
                val editServiceResult = withContext(Dispatchers.IO) {
                    maintenanceVehicleRepository.addData(
                        "services",
                        data = service,
                        id = service.id
                    )
                }
                if (editServiceResult is DataResult.Success) {

                }
            } catch (e: Exception) {

            }
        }
    }
}