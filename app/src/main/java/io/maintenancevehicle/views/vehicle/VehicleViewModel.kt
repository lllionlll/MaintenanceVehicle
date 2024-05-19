package io.maintenancevehicle.views.vehicle

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.maintenancevehicle.data.model.Vehicle
import io.maintenancevehicle.data.repository.MaintenanceVehicleRepository
import io.maintenancevehicle.utils.ToastFunction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class VehicleViewModel @Inject constructor(
    private val maintenanceVehicleRepository: MaintenanceVehicleRepository
) : ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val vehicleList = MutableLiveData<MutableList<Vehicle>>()
    val isAddSuccess = MutableLiveData<Boolean>()
    val isEditSuccess = MutableLiveData<Boolean>()
    val isDeleteSuccess = MutableLiveData<Boolean>()
    val vehicle = MutableLiveData<Vehicle>()

    fun editVehicles(context: Context, vehicle: Vehicle) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                withContext(Dispatchers.IO) {
                    maintenanceVehicleRepository.saveVehicles(listOf(vehicle))
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
    fun saveVehicles(context: Context, vehicle: Vehicle) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                withContext(Dispatchers.IO) {
                    maintenanceVehicleRepository.saveVehicles(listOf(vehicle))
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

    fun getVehicle() {
        viewModelScope.launch {
            try {
                isLoading.value = true
                vehicleList.value = withContext(Dispatchers.IO) {
                    maintenanceVehicleRepository.getVehicles()
                }.toMutableList()
                val randomDelay = (100..500).random().toLong()
                delay(randomDelay)
                isLoading.value = false
            } catch (e: Exception) {
                isLoading.value = false
            }
        }
    }

    fun getVehicleDetail(vehicleId: String) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val randomDelay = (100..500).random().toLong()
                delay(randomDelay)
                vehicle.value = withContext(Dispatchers.IO) {
                    maintenanceVehicleRepository.getVehicleById(vehicleId)
                }
                isLoading.value = false
            } catch (e: Exception) {
                isLoading.value = false
            }
        }
    }

    fun deleteVehicle(context: Context, vehicleId: String) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val randomDelay = (100..500).random().toLong()
                delay(randomDelay)
                withContext(Dispatchers.IO) {
                    maintenanceVehicleRepository.deleteVehicles(
                        listOf(vehicleId)
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