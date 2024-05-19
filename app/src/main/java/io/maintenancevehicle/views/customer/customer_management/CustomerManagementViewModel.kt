package io.maintenancevehicle.views.customer.customer_management

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.maintenancevehicle.data.model.Customer
import io.maintenancevehicle.data.DataResult
import io.maintenancevehicle.data.repository.MaintenanceVehicleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CustomerManagementViewModel @Inject constructor(
    private val maintenanceVehicleRepository: MaintenanceVehicleRepository
): ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val customerList = MutableLiveData<MutableList<Customer>>()

    fun getCustomers() {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val randomDelay = (100..500).random().toLong()
                delay(randomDelay)
                customerList.value = withContext(Dispatchers.IO) {
                    maintenanceVehicleRepository.getCustomers()
                }.toMutableList()
                isLoading.value = false
            } catch (e: Exception) {
                isLoading.value = false
            }
        }
    }
}