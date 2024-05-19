package io.maintenancevehicle.views.service

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.maintenancevehicle.data.model.Customer
import io.maintenancevehicle.data.model.Service
import io.maintenancevehicle.data.repository.MaintenanceVehicleRepository
import io.maintenancevehicle.utils.ToastFunction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ServiceMaintenanceViewModel @Inject constructor(
    private val maintenanceVehicleRepository: MaintenanceVehicleRepository
): ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val serviceList = MutableLiveData<MutableList<Service>>()
    val isEditSuccess = MutableLiveData<Boolean>()
    val isAddSuccess = MutableLiveData<Boolean>()
    val isDeleteSuccess = MutableLiveData<Boolean>()
    val service = MutableLiveData<Service>()

    fun editServices(context: Context, service: Service) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                withContext(Dispatchers.IO) {
                    maintenanceVehicleRepository.saveServices(listOf(service))
                }
                val randomDelay = (100..500).random().toLong()
                delay(randomDelay)
                ToastFunction.showMessage(context, "Sửa thành công!")
                isEditSuccess.value = true
                isLoading.value = false
            } catch (e: Exception) {
                isLoading.value = false
                ToastFunction.showMessage(context, "Sửa thất bại!")
            }
        }
    }

    fun saveServices(context: Context, service: Service) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                withContext(Dispatchers.IO) {
                    maintenanceVehicleRepository.saveServices(listOf(service))
                }
                val randomDelay = (100..500).random().toLong()
                delay(randomDelay)
                ToastFunction.showMessage(context, "Thêm thành công!")
                isAddSuccess.value = true
                isLoading.value = false
            } catch (e: Exception) {
                isLoading.value = false
                ToastFunction.showMessage(context, "Thêm thất bại!")
            }
        }
    }

    fun getServices() {
        viewModelScope.launch {
            try {
                isLoading.value = true
                serviceList.value = withContext(Dispatchers.IO) {
                    maintenanceVehicleRepository.getServices()
                }.toMutableList()
                val randomDelay = (100..500).random().toLong()
                delay(randomDelay)
                isLoading.value = false
            } catch (e: Exception) {
                isLoading.value = false
            }
        }
    }

    fun getServiceDetail(serviceId: String) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val randomDelay = (100..500).random().toLong()
                delay(randomDelay)
                service.value = withContext(Dispatchers.IO) {
                    maintenanceVehicleRepository.getServiceById(serviceId)
                }
                isLoading.value = false
            } catch (e: Exception) {
                isLoading.value = false
            }
        }
    }

    fun deleteService(context: Context, serviceId: String) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val randomDelay = (100..500).random().toLong()
                delay(randomDelay)
                withContext(Dispatchers.IO) {
                    maintenanceVehicleRepository.deleteServices(
                        listOf(serviceId)
                    )
                }
                ToastFunction.showMessage(context, "Xóa thành công!")
                isDeleteSuccess.value = true
                isLoading.value = false
            } catch (e: Exception) {
                ToastFunction.showMessage(context, "Xóa thất bại!")
                isLoading.value = false
            }
        }
    }
}