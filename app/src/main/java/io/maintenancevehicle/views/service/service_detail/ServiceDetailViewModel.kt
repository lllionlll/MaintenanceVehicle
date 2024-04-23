package io.maintenancevehicle.views.service.service_detail

import androidx.lifecycle.MutableLiveData
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
class ServiceDetailViewModel @Inject constructor(
    private val maintenanceVehicleRepository: MaintenanceVehicleRepository
) : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val service = MutableLiveData<Service>()

    fun getServiceDetail(serviceId: String) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val getServiceListResult = withContext(Dispatchers.IO) {
                    maintenanceVehicleRepository.getDetailById<Service>(
                        ref = "services",
                        id = serviceId
                    )
                }
                if (getServiceListResult is DataResult.Success) {
                    val serviceData = getServiceListResult.data
                    service.value = serviceData
                }
                isLoading.value = false
            } catch (e: Exception) {
                isLoading.value = false
            }
        }
    }

    fun deleteService(serviceId: String) {
        viewModelScope.launch {
            try {
                val deleteCustomerResult = withContext(Dispatchers.IO) {
                    maintenanceVehicleRepository.deleteData(
                        "services",
                        id = serviceId
                    )
                }
                if (deleteCustomerResult is DataResult.Success) {

                }
            } catch (e: Exception) {

            }
        }
    }

}