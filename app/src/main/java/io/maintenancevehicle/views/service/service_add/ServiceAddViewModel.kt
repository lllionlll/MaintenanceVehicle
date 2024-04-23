package io.maintenancevehicle.views.service.service_add

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
class ServiceAddViewModel @Inject constructor(
    private val maintenanceVehicleRepository: MaintenanceVehicleRepository
) : ViewModel() {

    fun addService(service: Service) {
        viewModelScope.launch {
            try {
                val addServiceResult = withContext(Dispatchers.IO) {
                    maintenanceVehicleRepository.addData(
                        "customers",
                        data = service,
                        id = service.id
                    )
                }
                if (addServiceResult is DataResult.Success) {

                }
            } catch (e: Exception) {

            }
        }
    }

}