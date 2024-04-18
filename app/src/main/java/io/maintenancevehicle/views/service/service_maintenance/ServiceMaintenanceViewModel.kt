package io.maintenancevehicle.views.service.service_maintenance

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.maintenancevehicle.data.model.Customer
import io.maintenancevehicle.data.model.Service
import io.maintenancevehicle.data.repository.MaintenanceVehicleRepository
import io.maintenancevehicle.data.source.remote.DataResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ServiceMaintenanceViewModel @Inject constructor(
    private val maintenanceVehicleRepository: MaintenanceVehicleRepository
): ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val customerList = MutableLiveData<MutableList<Service>>()

    fun getServices() {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val getServiceListResult = withContext(Dispatchers.IO) {
                    maintenanceVehicleRepository.getList<Service>("services")
                }
                if (getServiceListResult is DataResult.Success) {
                    val serviceListData = getServiceListResult.data
                    customerList.value = serviceListData
                }
                isLoading.value = false
            } catch (e: Exception) {
                isLoading.value = false
            }
        }
    }
}